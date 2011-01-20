package gizmoflow.components

import gizmoflow.{Stuff, StuffType}

/**
 *
 */
case class Reservoir(stuffType: StuffType) {

  var containedStuff: Stuff = stuffType.emptyStuff

}