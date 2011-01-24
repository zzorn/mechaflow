package gizmoflow.material

import phase._
import scalaquantity.Units._
import gizmoflow.PhysicsConstants._

/**
 * A fixed volume that contains some matter.
 */
class Cell(volume: Volume) {

  private var matters: Map[Material, Matter] = Map()
  private var _pressure: Pressure = 0

  def pressure = _pressure

  def add(material: Material, amount: Mass, temperature: Temperature) {

  }

  def remove(material: Material, amount: Mass, temperature: Temperature) {

  }

  /** Does heat exchange between contained materials */
  def doInternalHeatExchange(duration: Time) {

  }

  def averageTemperature: Temperature = {

  }

  /** Does heat exchange from external surfaces */
  def doExternalHeatExchange(duration: Time, externalContactSurfaces: List[(Area, Temperature)]) {

  }


  def updatePressure(duration: Time): Pressure = {

    val solidAndLiquidVolume: Volume = 0
    matters.values.foreach { matter: Matter =>
      matter.phase.state match {
        case Solid | Liquid => solidAndLiquidVolume += matter.volume
      }
    }

    var pressure: Pressure = 0
    if (solidAndLiquidVolume  > volume) {
      // Calculate pressure based on compressibility of the materials

      // The contribution of gas to this pressure is ignored..

      val overVolume = solidAndLiquidVolume - volume

      matters.values.foreach { matter: Matter =>
        matter.phase.state match {
          case Solid | Liquid  =>
            // Get part of volume
            val v = matter.volume
            val part = v / solidAndLiquidVolume

            // Compress it with that much (not entirely accurate, but maybe close enough for a timestepping simulation
            val neededVolumeCompression = overVolume * part
            pressure += part * (neededVolumeCompression / (v * matter.phase.compressibility))
        }
      }
    }
    else {
      // There is space for gas, calculate pressure from it
      val gasVolume: Volume = volume - solidAndLiquidVolume

      // Daltons atomic theory: The total pressure of a mixture of gases equals the sum of the pressures
      // that each would exert if it were present alone
      matters.values.foreach { matter: Matter =>
        matter.phase.state match {
          case Gaseous =>
            val moles: mol = matter.mass / matter.phase.molarMass

            // Universal gas law:
            pressure += (moles * UniversalGasConstant * matter.temperature) / gasVolume
        }
      }

    }


    // TODO: Later, Expand or shrink container (both from pressure difference and temperature expansion),
    //       and burst container if pressure is too high


    _pressure = pressure

  }


  // TODO: Add a doReactions method?

  

}