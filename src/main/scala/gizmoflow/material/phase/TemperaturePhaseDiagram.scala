package gizmoflow.material.phase

import scalaquantity.Units._

/**
 * Phase diagram based on melting and boiling point information for some material at atmospheric pressures.
 * Interpolates the same values for other pressures.
 */
class TemperaturePhaseDiagram(solid: Phase,
                              liquid: Phase,
                              gaseous: Phase,
                              meltingPoint: Temperature,
                              boilingPoint: Temperature) extends PhaseDiagram {
  def apply(temperature: Temperature, pressure: Pressure) {
    if (temperature < meltingPoint) solid
    else if (temperature > meltingPoint && temperature < boilingPoint) liquid
    else gaseous
  }
}