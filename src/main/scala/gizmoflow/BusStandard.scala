package gizmoflow

import localization.Tid

/**
 * Describes some bus standard.
 */
case class BusStandard(name: Symbol, ports: Map[Symbol, PortType]) extends PortType {
  
}
