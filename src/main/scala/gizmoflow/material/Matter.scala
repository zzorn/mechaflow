package gizmoflow.material

import scalaquantity.Units._

/**
 * Some amount of some (mix of) material.
 */
case class Matter(amount: kg, material: Material, temperature: Temperature) {
  
}
