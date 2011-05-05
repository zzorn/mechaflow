package gizmoflow.material.phase.diagram

import gizmoflow.util.QuantityUtils._

import scalaquantity.Units
import gizmoflow.material.phase.Phase
import org.scalaprops.Bean

/**
 * A phase diagram with just one phase.
 * Useful e.g. for composite materials such as wood that decompose in higher temperatures or pressures.
 */
class SinglePhaseDiagram() extends PhaseDiagram with Bean {

  val phase = p[Phase]('phase, null)


  def apply(temperature: Units.Temperature, pressure: Units.Pressure) = phase
}