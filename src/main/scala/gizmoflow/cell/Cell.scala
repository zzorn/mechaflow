package gizmoflow.cell

import scalaquantity.Units._
import gizmoflow.PhysicsConstants._
import gizmoflow.material.{Matter, Material}
import gizmoflow.material.phase._
import scalaquantity.Units

/**
 * A fixed volume that contains some matter.
 */
class Cell(val radius: Length, val length: Length) {

  lazy val volume: Volume = math.Pi * radius * radius * length
  private lazy val radius4: m~m~m~m = radius * radius * radius * radius
  private lazy val diameter3: m~m~m = 2*radius * 2*radius * 2*radius

  var cellPorts: List[CellPort] = Nil

  var externalAccelerationField: AccelerationField = NoAccelerationField

  private val endLaminarReynolds = 2300.0
  private val startTurbulentReynolds = 4000.0

  private var matters: Map[Material, Matter] = Map()
  private var _pressure: Pressure = 0

  private val Pi2 = math.Pi * math.Pi

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

  def calculateFlow() {
    cellPorts foreach calculateFlowToCenter(_)
  }

  /**
   * Calculates the Reynolds number for the flow speed from a cell port to the center
   * ( https://secure.wikimedia.org/wikipedia/en/wiki/Reynolds_number ).
   */
  def calculateReynoldsNumber(port: CellPort, matter: Matter): Double =
  {
    // Calculate Reynolds number
    val flow = if (port.inFlow < 0) -port.inFlow else port.inFlow
    val velocity = flow / port.area
    val density = matter.phase.density
    val Dh = port.hydraulicDiameter
    val dynamicViscosity = matter.phase.dynamicViscosity
    density * velocity * Dh / dynamicViscosity
  }

  /**
   * Calculate the flow from the specified port to the center of the cell.
   * The center of the cell is at origo.
   */
  private def calculateFlowToCenter(port: CellPort) {

    // Calculate acceleration force differential between the center and the port

    // For each material:
    matters.values.foreach { matter: Matter =>

      val reynolds = calculateReynoldsNumber(port, matter)// Calculate flow

      port.inFlow = if (reynolds < endLaminarReynolds) calculateLaminarFlowToCenter(port, matter)
                    else if (reynolds > startTurbulentReynolds)  calculateTurbulentFlowToCenter(port, matter)
                    else calculateInterpolatedFlow(port, matter, reynolds)
    }

  }

  def calculateInterpolatedFlow(port: CellPort, matter: Matter, reynolds: Double): m3/s = {
    // Just do a simple interpolation, actual behaviour is probably chaotic.
    val laminar = calculateLaminarFlowToCenter(port, matter)
    val turbulent = calculateTurbulentFlowToCenter(port, matter)

    val len = endLaminarReynolds - startTurbulentReynolds
    val t = (reynolds - endLaminarReynolds) / len
    laminar * (1.0 - t) + turbulent * t
  }

  /**
   * Calculates laminar flow from a cell port to the center, using the Hagen-Poiseuille equation
   * ( https://secure.wikimedia.org/wikipedia/en/wiki/Hagen-Poiseuille_flow ).
   */
  private def calculateLaminarFlowToCenter(port: CellPort, matter: Matter): m3/s = {
    // TODO: Include weight of matter into pressure
    val pressureDelta = port.pressure - pressure;
    (math.Pi * radius4 * pressureDelta)  /
    (8 * matter.phase.dynamicViscosity * port.distanceToCenter)
  }

  /**
   * Calculates turbulent flow from a cell port to the center, using the Darcy-Weisbach equation
   * ( https://secure.wikimedia.org/wikipedia/en/wiki/Darcy%E2%80%93Weisbach_equation ).
   */
  private def calculateTurbulentFlowToCenter(port: CellPort, matter: Matter): m3/s = {
    // TODO: Include weight of matter into pressure
    // TODO: Use matter volume to calculate diameter
    val pressureDelta = port.pressure - pressure;
    val density =  // TODO: For gases, depends on volume
    val flowRate2 = (Pi2 * pressureDelta * diameter3) /
                    (8 * f * port.distanceToCenter *  )


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