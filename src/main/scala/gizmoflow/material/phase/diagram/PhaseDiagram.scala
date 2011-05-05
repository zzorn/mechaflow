package gizmoflow.material.phase.diagram

import scalaquantity.Units._
import gizmoflow.util.QuantityUtils._
import gizmoflow.material.phase.Phase


/**
 * Contains information on what phase a material will have at a certain pressure and temperature.
 */
trait PhaseDiagram {

  def apply(temperature: Temperature, pressure: Pressure): Phase

}