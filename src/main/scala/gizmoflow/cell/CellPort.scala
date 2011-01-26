package gizmoflow.cell

import scalaquantity.Units._
import gizmoflow.Port

/**
 * Port in one cell that may be connected to a port in another cell.
 */
class CellPort(val area: Area, val position: (m, m, m)) extends Port[CellPort] {

  var pressure: Pressure = 0 * Pa;

  var flow: Volume/Time = 0*m3/s;

}
