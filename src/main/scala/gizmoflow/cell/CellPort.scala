package gizmoflow.cell

import scalaquantity.Units._
import gizmoflow.Port

/**
 * Port in one cell that may be connected to a port in another cell.
 */
class CellPort(val area: Area, val hydraulicDiameter: Length, val position: (m, m, m)) extends Port[CellPort] {

  var pressure: Pressure = 0 * Pa;

  var inFlow: Volume/Time = 0*m3/s;

  lazy val distanceToCenter: Length = {
    val x: Double = position._1.value
    val y: Double = position._2.value
    val z: Double = position._3.value
    math.sqrt(x*x + y*y + z*z) * m
  }

}
