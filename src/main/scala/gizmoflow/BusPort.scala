package gizmoflow

/**
 * A collection of several ports according to some BusStandard,
 * for transfer of several types of Subjects between the same nodes.
 */
case class BusPort(name: Symbol,
                   node: Node,
                   portType: BusStandard,
                   ports: Map[Symbol, Port]) extends Port {
  
}
