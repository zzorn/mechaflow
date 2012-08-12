package org.mechaflow.primitives

/**
 *
 */
case class ClassDefinition(id: Symbol,
                           doc: Option[String],
                           imports: List[Path],
                           extensions: List[Path],
                           elements: List[Element],
                           equations: List[Equation]) {

}