package gizmoflow.cell

import scalaquantity.Units._

/**
 * Encapsulates an acceleration field of some magnitude and direction.
 * E.g. the gravitation of a planet.
 */
case class AccelerationField(x: Double, y: Double, z: Double) {
  
}


object NoAccelerationField extends AccelerationField((0.0 * m) / (1.0*s2), 0 * m/s2, 0 * m/s2)