package gizmoflow

/**
 * Has a connected receive and send reservoir in case of fluids, with some pressure and calculated change over time from
 * all connected ports or processes.
 */
case class SinglePort(name: Symbol, node: Node, portType: SinglePortType) extends Port {
  
}