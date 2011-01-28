package gizmoflow.cell

import scalaquantity.Units._

/**
 * Encapsulates an acceleration field of some magnitude and direction.
 * E.g. the gravitation of a planet.
 */
case class AccelerationField(x: Acceleration, y: Acceleration, z: Acceleration) {
  
}


object NoAccelerationField extends AccelerationField(0 * m/s2, 0 * m/s2, 0 * m/s2)