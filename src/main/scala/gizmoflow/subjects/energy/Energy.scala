package gizmoflow.subjects.energy

import gizmoflow.Subject

/**
 * Energy, transferred in some form.
 */
trait Energy extends Subject {
  def transferredUnit = 'Watt
}