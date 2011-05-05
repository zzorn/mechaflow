package gizmoflow.material

import phase.diagram.{SinglePhaseDiagram, PhaseDiagram}
import scalaquantity.Units._
import org.scalaprops.Bean

/**
 * Some basic material.
 */
class Material() extends Bean {

  val name = p('name, "")
  val phases = p[PhaseDiagram]('phases, new SinglePhaseDiagram())


}