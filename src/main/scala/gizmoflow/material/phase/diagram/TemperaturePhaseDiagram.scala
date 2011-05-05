package gizmoflow.material.phase.diagram

import scalaquantity.Units._
import gizmoflow.util.QuantityUtils._
import gizmoflow.material.phase.Phase
import org.scalaprops.Bean


/**
 * Phase diagram based on melting and boiling point information for some material at atmospheric pressures.
 * Interpolates the same values for other pressures.
 */
class TemperaturePhaseDiagram() extends PhaseDiagram with Bean {

  val solid = p('solid, new Phase())
  val liquid = p('liquid, new Phase())
  val gaseous = p('gaseous, new Phase())

  val meltingPoint = p('meltingPoint, new Temperature(fromCelsius(0)))
  val boilingPoint = p('boilingPoint, new Temperature(fromCelsius(100)))


  def apply(temperature: Temperature, pressure: Pressure) {
    if (temperature < meltingPoint()) solid
    else if (temperature > meltingPoint() && temperature < boilingPoint()) liquid
    else gaseous
  }
}