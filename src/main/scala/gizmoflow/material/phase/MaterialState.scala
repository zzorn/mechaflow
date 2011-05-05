package gizmoflow.material.phase

object MaterialState {
  def states: List[MaterialState] = List(Solid, Liquid, Gaseous)
}

sealed trait MaterialState

case object Solid extends MaterialState
case object Liquid extends MaterialState
case object Gaseous extends MaterialState
//object Plasma extends MaterialState
//object Supercritical extends MaterialState
