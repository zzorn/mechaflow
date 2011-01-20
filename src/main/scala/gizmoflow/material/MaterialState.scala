package gizmoflow.material

sealed trait MaterialState

object Solid extends MaterialState
object Liquid extends MaterialState
object Gaseous extends MaterialState
