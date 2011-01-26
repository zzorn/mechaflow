package gizmoflow.cell

import scalaquantity.Units._
import gizmoflow.PhysicsConstants._
import gizmoflow.material.{Matter, Material}
import gizmoflow.material.phase._

/**
 * A fixed volume that contains some matter.
 */
class Cell(volume: Volume) {

  private var matters: Map[Material, Matter] = Map()
  private var _pressure: Pressure = 0

  def pressure = _pressure

  def simulateTick(duration: Time) {
    updatePressure(duration);

    // TODO: Calculate pressure at each port
  }
  
  def simulateTock(duration: Time) {
    // TODO: Calculate flow using Bernoullis principle


  }
  
  
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

      /*
         Generalized from solved three material equation:
         p = (deltaVolume/Volume) / (materialMass1 / materialDensity1 * materialCompressibility1 +
                                     materialMass2 / materialDensity2 * materialCompressibility2 +
                                      ...
                                     materialMassN / materialDensityN * materialCompressibilityN )
       */

      val overVolume = solidAndLiquidVolume - volume

      var constantSum: Volume/Pressure = 0 * m3/Pascal
      matters.values.foreach { matter: Matter =>
        matter.phase.state match {
          case Solid | Liquid =>
            val v = matter.volume
            val c = matter.phase.compressibility
            constantSum += v * c
        }
      }

      if (constantSum == 0) pressure = 0
      else pressure = (overVolume.value / volume.value) / constantSum
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

    // TODO: Effect of external force field (acceleration, gravitation)
    

    // TODO: Later, Expand or shrink container (both from pressure difference and temperature expansion),
    //       and burst container if pressure is too high


    _pressure = pressure

  }


  // TODO: Add a doReactions method?

  

}