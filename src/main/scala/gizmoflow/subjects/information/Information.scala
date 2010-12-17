package gizmoflow.subjects.information

import gizmoflow.Subject

/**
 * Abstract information, transferred with/on some medium.
 */
trait Information extends Subject {
  def magnitudeUnit = 'Bandwidth
  def flowUnit = 'Speed
  def transferredUnit = 'Bit
}