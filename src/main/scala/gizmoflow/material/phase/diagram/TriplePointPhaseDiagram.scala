package gizmoflow.material.phase.diagram

import scalaquantity.Units._

import gizmoflow.util.QuantityUtils._
import gizmoflow.material.phase.Phase
import org.scalaprops.Bean

/**
 * A phase diagram that uses a known triple point and curve slope constants to map out a phase diagram.
 * triplePoint is the point of pressure and temperature where all three phases can transition to each other.
 * solidGasPoint is a point somewhere along the line where solid and gas can transition to each other,
 * and similarly for solidLiquidPoint and liquidGas point.
 * The diagram is linearly interpolated using these four points.
 *
 * Assumes that the solid, liquid, and gaseous phases are positioned in this order around the triple point:
 *
 * ^
 * |       *<solidLiquidPoint
 * |       |
 * p       |   liquid
 * r       |
 * e solid |triple point
 * s       *--------------*<liquidGasPoint
 * s      /
 * u     /
 * r    /       gas
 * e   /
 *    *
 *    ^solidGasPoint
 *
 * +    temperature ->
 *
 */
class TriplePointPhaseDiagram() extends PhaseDiagram with Bean {

  val solid = p('solid, new Phase())
  val liquid = p('liquid, new Phase())
  val gaseous = p('gaseous, new Phase())

  val triplePoint = p('triplePoint, new PhasePoint())
  val solidGasPoint = p('solidGasPoint, new PhasePoint())
  val solidLiquidPoint = p('solidLiquidPoint, new PhasePoint())
  val liquidGasPoint = p('liquidGasPoint, new PhasePoint())

  val solidGasAngle    = solidGasPoint().angleFrom(triplePoint())
  val solidLiquidAngle = solidLiquidPoint().angleFrom(triplePoint())
  val liquidGasAngle   = liquidGasPoint().angleFrom(triplePoint())

  def apply(temperature: Temperature, pressure: Pressure) {
    val angle = math.atan2(pressure - triplePoint().pressure(),
                           temperature - triplePoint().temperature())

    if (angleBetween(angle, solidGasAngle, solidLiquidAngle)) solid
    else if (angleBetween(angle, solidLiquidAngle, liquidGasAngle)) liquid
    else gaseous
  }

  private def angleBetween(angle: Double, a: Double, b: Double): Boolean = {
    if (a <= b) a <= angle && angle < b
    else (a <= angle && angle <= Tau) || (0 <= angle && angle < b)
  }
  
}