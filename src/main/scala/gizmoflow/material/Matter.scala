package gizmoflow.material

import phase.Phase
import scalaquantity.Units._

/**
 * Some amount of some material at a specific temperature.
 */
case class Matter(material: Material,
                  var mass: kg,
                  var temperature: Temperature,
                  var phase: Phase) {

  def updatePhase(duration: Time, pressure: Pressure) {
    phase = material.phases(temperature, pressure)
  }

  def density: Density = {
    // Assume density is specified at 20 degrees celsius.
    phase.density * phase.volumetricThermalExpansion * (temperature - fromCelsius(20))
  }

  def volume: Volume = density * mass
  
}
