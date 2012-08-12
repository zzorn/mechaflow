package org.mechaflow.parser

import org.mechaflow.utils.language.LanguageParser
import org.mechaflow.primitives._
import util.parsing.combinator.ImplicitConversions
import org.mechaflow.primitives.ClassDefinition
import org.mechaflow.primitives.Module
import org.mechaflow.primitives.Path

/**
 * Parses a physical specification language.
 * It's similar to but simpler than the Modelica ( http://www.modelica.org ) language.
 */
class MechaParser() extends LanguageParser[Module] {

  val PACKAGE = registerKeyword("package")
  val CLASS = registerKeyword("class")
  val EXTENDS = registerKeyword("extends")
  val IMPORT = registerKeyword("import")

  val InputKeyword = "input"
  val OutputKeyword = "output"
  val ConstKeyword = "const"
  val ParamKeyword = "param"

  val VAR = registerKeyword("var")
  val FLOW = registerKeyword("flow")
  val CONSTANT = registerKeyword(ConstKeyword)
  val PARAMETER = registerKeyword(ParamKeyword)
  val INPUT = registerKeyword(InputKeyword)
  val OUTPUT = registerKeyword(OutputKeyword)
  val EQUATIONS = registerKeyword("equations")
  val CONNECT = registerKeyword("connect")

  val OR = registerKeyword("or")
  val AND = registerKeyword("and")
  val NOT = registerKeyword("not")
  val TRUE = registerKeyword("true")
  val FALSE = registerKeyword("false")

  val DER = registerKeyword("der")

  val IF = registerKeyword("if")
  val THEN = registerKeyword("then")
  val ELSE = registerKeyword("else")

  registerDelimiters(
    "=", ",", ":", ";", ".",
    "(", ")", "[", "]", "{", "}",
    "->", "=>",
    "+", "-", "*", "/", "^",
    "<", ">", "<=", ">=", "!=", "==", "<>"
  )

  def rootParser: PackratParser[Module] = moduleParser

  // Stored definitions
  private lazy val moduleParser: PackratParser[Module] = {
    opt(PACKAGE ~> pathParser("package")) ~ rep(classDefinitionParser <~ opt(";")) ^^
      {case p ~ cs => Module(p, cs)}
  }

  // Class definition
  private lazy val classDefinitionParser:  PackratParser[ClassDefinition] = {
    CLASS ~> identifier("class name") ~ docStringParser ~ "{" ~
      imports ~
      extensions ~
      elements ~
      equations <~ "}" ^^
      {case id ~ doc ~ _b ~ ims ~ exs ~ els ~ eqs => ClassDefinition(id, doc, ims, exs, els, eqs)}
  }

  // Imports
  private lazy val imports:  PackratParser[List[Import]] = rep(importParser)
  private lazy val importParser:  PackratParser[Import] = IMPORT ~> pathParser("import statement") ^^ {case p => Import(p)}

  // Extend clauses
  private lazy val extensions:  PackratParser[List[Extends]] = rep(extendsParser)
  private lazy val extendsParser:  PackratParser[Extends] = EXTENDS ~> pathParser("parent class") ^^ {case p => Extends(p)}

  // Elements
  private lazy val elements:  PackratParser[List[Element]] = rep(elementParser)
  private lazy val elementParser:  PackratParser[Element] = {
    opt(FLOW) ~
    opt(INPUT | OUTPUT) ~
    (CONSTANT | PARAMETER | VAR) ~
    pathParser("type") ~
    identifier("element name") ~
      opt(assignmentParser) ~
      docStringParser ^^
      {case f~io~cpv~ty~id~as~doc =>
        Element(id, ty, as, doc,
          flow  = f.isDefined,
          in    = io.isDefined && io == InputKeyword,
          out   = io.isDefined && io == OutputKeyword,
          const = cpv == ConstKeyword,
          param = cpv == ParamKeyword) }
  }

  private lazy val assignmentParser:  PackratParser[Assignment] = {
    "=" ~> expressionParser ^^
      {case e => ExpressionAssignment(e).asInstanceOf[Assignment]}
  }

  // Equations
  private lazy val equations:  PackratParser[List[Equation]] = opt(EQUATIONS ~> rep(equationParser)) ^^ {case es => es.getOrElse(Nil)}
  private lazy val equationParser:  PackratParser[Equation] = {
    CONNECT ~> "(" ~> componentReference ~ "," ~ componentReference <~ ")" ^^
      {case a ~ _c ~ b => Connection(a, b)} |
    simpleExpressionParser ~ "=" ~ expressionParser ^^
      {case left ~ _eq ~ right => SimpleEquation(left, right) }
  }

  // Expressions
  private lazy val expressionParser:  PackratParser[Expr] = {
    ifExpression | simpleExpressionParser
  }

  private lazy val ifExpression:  PackratParser[Expr] = {
    IF ~> simpleExpressionParser ~ THEN ~ simpleExpressionParser ~ ELSE ~ expressionParser ^^
      {case c ~ _t ~ t ~ _e ~ e => IfExpr(c, t, e)}
  }

  private lazy val simpleExpressionParser:  PackratParser[Expr] = {
    logicalExpressionParser
  }

  // Logic
  private lazy val logicalExpressionParser:  PackratParser[Expr] = {
    rep1sep(logicalTerm, OR) ^^ {case ts => OrExpr(ts)}
  }

  private lazy val logicalTerm :  PackratParser[Expr] = {
    rep1sep(logicalFactor, AND) ^^ {case fs => AndExpr(fs)}
  }

  private lazy val logicalFactor:  PackratParser[Expr] = {
    NOT ~> relation ^^ {case e => NotExpr(e)} |
    relation
  }

  // Relations
  private lazy val relation:  PackratParser[Expr] = {
    arithmeticExpression ~ relationOp ~ arithmeticExpression ^^ {case a~op~b => RelationExpr(a, op, b)} |
    arithmeticExpression
  }

  private lazy val relationOp:  PackratParser[Symbol] = {
    (">" | ">=" | "<" | "<=" | "==" | "<>") ^^ {Symbol(_)}
  }


  // Terms
  private lazy val arithmeticExpression:  PackratParser[Expr] = {
    "-" ~> posExpr ^^ {case e => NegExpr(e)} |
    posExpr
  }

  private lazy val posExpr:  PackratParser[Expr] = {
    term ~ rep(termElement) ^^ {case f~r => TermExpr(f, r)}
  }

  private lazy val termElement: PackratParser[TermElement] = {
    addOp ~ term ^^ {case op~t => TermElement(op, t)}
  }

  private lazy val addOp:  PackratParser[Symbol] = {
    ("+" | "-") ^^ {Symbol(_)}
  }


  // Factors
  private lazy val term:  PackratParser[Expr] = {
    factor ~ rep(factorElement) ^^ {case f~r => FactorExpr(f, r)}
  }

  private lazy val factorElement: PackratParser[FactorElement] = {
    mulOp ~ factor ^^ {case op~f => FactorElement(op, f)}
  }

  private lazy val mulOp:  PackratParser[Symbol] = {
    ("*" | "/") ^^ {Symbol(_)}
  }

  // Exponentiation
  private lazy val factor:  PackratParser[Expr] = {
    primary ~ "^" ~ primary ^^ {case b ~ _o ~ e => ExponentExpr(b, e)} |
    primary
  }

  // Primary elements
  private lazy val primary:  PackratParser[Expr] = {
    numLiteral |
    booleanLiteral |
    parens |
    derivation |
    functionCall |
    variableReference
  }

  // Parens
  private lazy val parens: PackratParser[Expr] = {
    "(" ~> expressionParser <~ ")"
  }

  // Literals
  private lazy val booleanLiteral: PackratParser[Expr] = {
    TRUE  ^^^ (TrueExpr)  |
    FALSE ^^^ (FalseExpr)
  }

  private lazy val numLiteral: PackratParser[Expr] = {
    doubleNumber ^^ {NumExpr(_)}
  }

  // Derivation
  private lazy val derivation: PackratParser[Expr] = {
    DER ~> "(" ~> expressionParser <~")"  ^^ {case e => DerExpr(e)}
  }

  // Variable reference
  private lazy val variableReference: PackratParser[Expr] = {
    pathParser("variable name") ^^ {case path => VariableRef(path)}
  }

  // Function call
  private lazy val functionCall: PackratParser[Expr] = {
    pathParser("function name") ~ "(" ~ repsep(argument, ",")  <~")" ^^ {case id ~ _lp ~ as => CallExpr(id, as)}
  }

  private lazy val argument: PackratParser[Argument] = {
    namedArgument | unnamedArgument
  }

  private lazy val namedArgument: PackratParser[NamedArgument] = {
    identifier("argument name") ~ "=" ~ expressionParser ^^{case n ~ _eq ~ v => NamedArgument(n, v)}
  }

  private lazy val unnamedArgument: PackratParser[UnnamedArgument] = {
    expressionParser ^^{case v => UnnamedArgument(v)}
  }


  // Docstrings
  private lazy val docStringParser: PackratParser[Option[String]] = opt(stringLit)


  // Reference
  private def componentReference: PackratParser[Ref] = {
    rep1sep(identifier("reference"), ".") ^^ {case l => Ref(l)}
  }

  // Path
  private def pathParser(context: String): PackratParser[Path] = {
    rep1sep(identifier(context), ".") ^^ {case l => Path(l)}
  }

  // Named identifier
  private def identifier(context: String): PackratParser[Symbol] = {
    ident ^^ {Symbol(_)} |
    failure("Expected " + context)
  }

}
