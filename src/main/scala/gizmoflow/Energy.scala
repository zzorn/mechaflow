package gizmoflow

import scalaquantity.Units._

/**
 * Some amount of energy of some type.
 */
case class Energy(amount: Joule, energyType: Symbol) extends Stuff(energyType) {
  
}
