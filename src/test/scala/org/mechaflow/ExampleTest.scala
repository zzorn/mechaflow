package org.mechaflow

import org.scalatest.FunSuite
import parser.{Scope, MechaAnalyser, MechaParser}
import primitives._
import primitives.Real

/**
 *
 */
class ExampleTest extends FunSuite {

  val testProgram =
        """
          |package electric.basic
          |
          |class Pin "Electrical pin" {
          |  var Real volt = 0;
          |  flow var Real current = 0;
          |}
          |
          |class TwoPinComponent {
          |  var Pin p "Positive pin"
          |  var Pin n "Negative pin"
          |  var Real volt
          |  var Real current
          |  equations
          |    volt = p.volt - n.volt
          |    p.current + n.current = 0;
          |    current = p.current;
          |}
          |
          |class Resistor "Ideal resistor" {
          |  extends TwoPinComponent
          |  param Real resistance = 200 "Resistance in ohms"
          |  equations
          |    current = voltage / resistance
          |}
          |
          |class VoltageSource "A voltage source with fixed voltage" {
          |  extends TwoPinComponent
          |  param Real volt = 5
          |}
          |
          |class SimpleCircuit "Test circuit" {
          |  var VoltageSource v1
          |  var Resistor r1
          |  var Resistor r2 // (resistance = 400)  TODO: Allow overriding elements in type class.
          |  equations
          |    connect(v1.p, r1.n)
          |    connect(r1.p, r2.n)
          |    connect(r2.p, v1.n)
          |}
          |
        """.stripMargin

  test("Parse sources") {
    val parser = new MechaParser()

    val result = parser.parseString(testProgram)

    println(result)
    assert(result != null)

  }

  test("Designing API") {

    // Resistor example

    val pin = new Component('Pin, "Electric pin", connector = true) {
      val voltage = addVar(Var('voltage, Real(0)))
      val current = addVar(Var('current, Real(0), flow = true))
    }

    val twoPinComponent = new Component('twoPinComponent, "Base class for two pin components") {
      val p = addVar(Var('p, pin.newInstance()))
      val n = addVar(Var('n, pin.newInstance()))
      val voltage = addVar(Var('voltage, Real(0)))
      addVar(Var('current, Real(0)))
      addEq(ParsedEquation("voltage = p.voltage - n.voltage"))
      addEq(ParsedEquation("p.current + n.current = 0"))
      addEq(ParsedEquation("current = p.current"))
    }

    val resistor = new Component('Resistor, parents = List(twoPinComponent)) {
      val resistance = addVar(Var('resistance, Real(200), parameter = true))
      addEq(ParsedEquation("current = voltage / resistance"))
    }


    val voltageSource = new Component('VoltageSource, "A voltage source with fixed voltage", List(twoPinComponent)) {
      val volt = addVar(Var('volt, Real(5), parameter = true))
      addEq(ParsedEquation("voltage = volt"))
    }

    val simpleCircuit = new Component('SimpleCircuit)
    simpleCircuit.addVar(Var('V1, voltageSource.newInstance(Map('volt -> Real(7)))))
    simpleCircuit.addVar(Var('R1, resistor.newInstance(Map('resistance -> Real(400)))))
    simpleCircuit.addVar(Var('R2, resistor.newInstance()))
    simpleCircuit.connect(Path(List('v1, 'p)), Path(List('r1, 'n)))
    simpleCircuit.connect(Path(List('r1, 'p)), Path(List('r2, 'n)))
    simpleCircuit.connect(Path(List('r2, 'p)), Path(List('v1, 'n)))


    print(simpleCircuit)

  }


  test("Test analyser") {
    val analyzer = new MechaAnalyser()
    val scope = analyzer.loadString(testProgram)

    assert(scope.getNode(List('Resistor, 'resistance)) != None)
    val r1: Option[Node] = scope.getNode(List('SimpleCircuit, 'r1))
    val r1scope: Option[Scope] = scope.getScope(List('SimpleCircuit))
    assert(r1scope != None)
    assert(r1scope.get.getNode(List('r2)) != None)
    assert(r1scope.get.getNode(List('VoltageSource, 'volt)) != None)
    assert(r1scope.get.getNode(List('resistance)) === None)
    assert(r1scope.get.getNode(List('p)) != None)

  }


}
