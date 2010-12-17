package gizmoflow

/**
 * Something that can be transferred through a connection between two ports.
 */
trait Subject {
 
  def transferredUnit: Symbol
  def flowUnit: Symbol
  def magnitudeUnit: Symbol
}