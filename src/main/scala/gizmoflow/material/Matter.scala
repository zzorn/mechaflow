package gizmoflow.material

import phase.Phase
import scalaquantity.Units._

/**
 * Some amount of some material at a specific temperature.
 */
case class Matter(material: Material,
                  var mass: kg,
                  var temperature: Temperature) {

  def phase(pressure: Pressure): Phase = material.phases(temperature, pressure)

  def density(pressure: Pressure): Density = {
    val p: Phase = phase(pressure)
    // Assume density is specified at 20 degrees celsius.
    p.density * p.volumetricThermalExpansion * (temperature - fromCelsius(20))
  }


  
}
