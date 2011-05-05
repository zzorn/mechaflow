package gizmoflow.material.phase.diagram

import org.scalaprops.Bean
import scalaquantity.Units._

import gizmoflow.util.QuantityUtils._
import gizmoflow.material.phase.Phase

/**
 * 
 */

class PhasePoint extends Bean {

  val pressure = p('pressure, new Pressure(0))
  val temperature = p('pressure, fromCelsius(0))

  def angleFrom(origin: PhasePoint): Double =
    math.atan2(pressure()    - origin.pressure(),
               temperature() - origin.temperature())

}