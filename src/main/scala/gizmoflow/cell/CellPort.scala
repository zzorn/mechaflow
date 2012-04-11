package gizmoflow.cell

import gizmoflow.Port

/**
 * Port in one cell that may be connected to a port in another cell.
 */
class CellPort(val area: Double, val hydraulicDiameter: Double, val position: (Double, Double, Double)) extends Port[CellPort] {

  var pressure_Pa: Double = 0;

  var inFlow_m3_per_s: Double = 0;

  lazy val distanceToCenter: Double = {
    val x: Double = position._1
    val y: Double = position._2
    val z: Double = position._3
    math.sqrt(x*x + y*y + z*z)
  }

}
