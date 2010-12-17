package gizmoflow

import localization.Tid

/**
 * Some type of port that can be used to move one type of subject.
 *
 * maxFlow is used as the maximum value for the movement of units of the subject per second,
 * e.g. max electric current, max rotation speed, max signal speed, max fluid flow, max items moved per seconds.
 *
 * maxMagnitude is used for the maximum value of the rotational torque, electrical voltage,
 * fluid pressure, item size, and data size.
 *
 */
case class SinglePortType(name: Symbol,
                          transferredSubjet: Subject,
                          maxFlow: Double,
                          maxMagnitude: Double) extends PortType {
  
}