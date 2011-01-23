package gizmoflow.material.phase

sealed trait MaterialState

object Solid extends MaterialState
object Liquid extends MaterialState
object Gaseous extends MaterialState
object Plasma extends MaterialState
object Supercritical extends MaterialState
