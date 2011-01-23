package gizmoflow.material

import phase._
import scalaquantity.Units._

/**
 * A fixed volume that contains some matter.
 */
class Cell(volume: Volume) {

  private var matters: Map[Material, Matter] = Map()

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

    val solidVolume: Volume = 0
    val liquidVolume: Volume = 0
    matters.values.foreach { matter: Matter =>
      matter.phase.state match {
        case Solid  => solidVolume  += matter.volume
        case Liquid => liquidVolume += matter.volume
      }
    }

    if (solidVolume + liquidVolume > volume) {
      // calculate pressure

      // TODO: Later, Expand container, calculate pressure, burst container if too high
      
    }


    // Solids

    // Liquids


    // Gases

  }


  // TODO: Add a doReactions method?

  

}