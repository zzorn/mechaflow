package gizmoflow.material

import phase.{Gaseous, Phase}
import scalaquantity.Units._
import scalaquantity.{Units, Exponents}

/**
 * Some amount of some material at a specific temperature.
 */
case class Matter(material: Material,
                  var mass: kg,
                  var temperature: Temperature,
                  var phase: Phase,
                  var volume: Volume) {

  def updatePhase(duration: Time, pressure: Pressure) {
    phase = material.phases(temperature, pressure)
  }

  def normalDensity = phase.density * phase.volumetricThermalExpansion * (temperature - fromCelsius(20)) // Assume density is specified at 20 degrees celsius.

  def density: Density = {
    val calculatedDensity = mass / volume
    if (phase.state == Gaseous) calculatedDensity
    else {
      // If the matter has been compressed, return the compressed density, otherwise the normal one
      val nd = normalDensity
      if (calculatedDensity > nd) calculatedDensity else nd
    }
  }


}
