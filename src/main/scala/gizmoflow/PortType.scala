package gizmoflow

/**
 * Either a bus standard, or a specific Subject.
 */
trait PortType {
  def name: Symbol
}
