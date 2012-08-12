package org.mechaflow.primitives

/**
 *
 */
case class Element(id: Symbol,
                   typePath: Path,
                   initialValue: Option[Assignment],
                   doc: Option[String],
                   flow: Boolean = false,
                   in: Boolean = false,
                   out: Boolean = false,
                   const: Boolean = false,
                   param: Boolean = false) {

}