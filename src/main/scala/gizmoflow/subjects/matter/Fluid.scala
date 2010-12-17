package gizmoflow.subjects.matter

/**
 * 
 */
trait Fluid extends Matter {
  def transferredUnit = 'Mass // Kg
  def magnitudeUnit = 'Pressure // Pa
  def flowUnit = 'KgPerSecond
}