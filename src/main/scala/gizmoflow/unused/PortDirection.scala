package gizmoflow

/**
 * Direction of a port (in/out/both/closed).
 */
sealed trait PortDirection {
  def canConnectTo(direction: PortDirection) =
    (hasInDirection && direction.hasOutDirection) ||
    (hasOutDirection && direction.hasInDirection)
  
  def hasInDirection: boolean
  def hasOutDirection: boolean
}

object InDirection extends PortDirection {
  val hasInDirection = true
  val hasOutDirection = false
}

object OutDirection extends PortDirection {
  val hasInDirection = false
  val hasOutDirection = true
}

object InOutDirection extends PortDirection {
  val hasInDirection = true
  val hasOutDirection = true
}

object NoDirection extends PortDirection {
  val hasInDirection = false
  val hasOutDirection = false
}
