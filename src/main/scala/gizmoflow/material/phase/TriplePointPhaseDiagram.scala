package gizmoflow.material.phase

import scalaquantity.Units._

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
class TriplePointPhaseDiagram(solid: Phase,
                              liquid: Phase,
                              gaseous: Phase,
                              triplePoint: (Pressure, Temperature),
                              solidGasPoint: (Pressure, Temperature),
                              solidLiquidPoint: (Pressure, Temperature),
                              liquidGasPoint: (Pressure, Temperature)) extends PhaseDiagram {

  val solidGasAngle    = math.atan2(solidGasPoint._1    - triplePoint._1, solidGasPoint._2    - triplePoint._2)
  val solidLiquidAngle = math.atan2(solidLiquidPoint._1 - triplePoint._1, solidLiquidPoint._2 - triplePoint._2)
  val liquidGasAngle   = math.atan2(liquidGasPoint._1   - triplePoint._1, liquidGasPoint._2   - triplePoint._2)

  def apply(temperature: Temperature, pressure: Pressure) {
    val angle = math.atan2(pressure - triplePoint._1,
                           temperature - triplePoint._2)

    if (angleBetween(angle, solidGasAngle, solidLiquidAngle)) solid
    else if (angleBetween(angle, solidLiquidAngle, liquidGasAngle)) liquid
    else gaseous
  }

  private def angleBetween(angle: Double, a: Double, b: Double): Boolean = {
    if (a <= b) a <= angle && angle < b
    else (a <= angle && angle <= Tau) || (0 <= angle && angle < b)
  }
  
}