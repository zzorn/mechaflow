package gizmoflow.material.phase

import scalaquantity.Units._

/**
 * Contains information on what phase a material will have at a certain pressure and temperature.
 */
trait PhaseDiagram {

  def apply(temperature: Temperature, pressure: Pressure): Phase

}