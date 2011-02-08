package gizmoflow

/**
 * The type of energy or matter that is transported between Nodes through Ports.
 */
sealed trait StuffType

case object FluidStuff       extends StuffType
case object MatterStuff      extends StuffType
case object ElectricityStuff extends StuffType
case object RotationStuff    extends StuffType

