package gizmoflow.material.phase

import scalaquantity.Units

/**
 * A phase diagram with just one phase.
 * Useful e.g. for composite materials such as wood that decompose in higher temperatures or pressures.
 */
case class SinglePhaseDiagram(phase: Phase) extends PhaseDiagram {
  def apply(temperature: Units.Temperature, pressure: Units.Pressure) = phase
}