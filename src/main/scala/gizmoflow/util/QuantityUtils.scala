package gizmoflow.util

import scalaquantity.Units.Quantity
import scalaquantity.Exponents._
import org.scalaprops.Property

/**
 * 
 */

object QuantityUtils {

  implicit def quantityToDouble(q: Quantity[_, _, _, _, _, _, _]): Double = q.value
  implicit def propertyToValue[T](prop: Property[T]): T = prop.value

}