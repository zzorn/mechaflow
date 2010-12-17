package gizmoflow.subjects

import matter.Matter

/**
 * Individual unique item instances.  Transferred for example on a conveyor belt.
 */
class Items extends Matter {
  def transferredUnit = 'Item
  def magnitudeUnit = 'One
  def flowUnit = 'ItemsPerSecond
}