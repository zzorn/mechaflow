package org.mechaflow.primitives

/**
 * Represents a connection between two connectable components of matching types.
 * Modeled as an equality (or zero sum for flow variables) between the variables in the connectors.
 */
case class Connection(fromComponent: Path, toComponent: Path) extends Equation {

  /* Some checking logic for when it is instantiated..
    if (!from.isInstanceOf[Component]) throw new Error("Must connect from a component, can not connect to a value of type " + from.getClass)
    if (!to.isInstanceOf[Component]) throw new Error("Must connect to a component, can not connect to a value of type " + to.getClass)

    val fromComponent: Component = from.asInstanceOf[Component]
    val toComponent: Component = to.asInstanceOf[Component]
    if (!fromComponent.isConnector) throw new Error("To connect from a component, it must be marked as a connector or inherit a connector.")
    if (!toComponent.isConnector) new Error("To connect to a component, it must be marked as a connector or inherit a connector.")

   */

}