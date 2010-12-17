package gizmoflow.subjects

import energy.Energy

/**
 * Rotational energy, transferred with axle, belt, piston.
 */
class Rotation extends Energy {
  def magnitudeUnit = 'Torque  // Newtonmeters
  def flowUnit = 'AngularVelocity  // 1/seconds
}