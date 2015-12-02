package org.mechaflow2

import org.flowutils.time.Time

/**
 * Connection available on a module.
 * Unique for each module instance.
 */
interface Port {

    /*

    TODO: Things like connector sizes or techs should be parametrizable for machines, so not something they should
          have to specify statically..
    TODO: So there should rather be factories for creating machine components based on specifications, and with methods to
          query the materials and knowledge, skills, and work required for building a certain parametrization.?
          */

    /*
    Performance parameters internal to the port.
      - Depends on the precision and quality of construction, and the materials used (strength, solidity, elasticity? etc).
      - Max torque & speed / volt & amp, pressure & flow,
      - Max item size
      - Max signal frequency / data size

      - Leakage of fluids (pore size?)
      - Friction -> heat
      - Fluid flow -> heat
      - signal -> drop
      - (items -> ejected?)


     */


    /**
     * User readable name/id of the port.
     */
    val name: String

    /**
     * User readable description of the port.
     */
    val description: String?

    /**
     * The direction of energy, matter or information transmission in this port.
     */
    val direction: PortDirection

    /**
     * @return size of the port, expressed as 2^sizeGauge meters.
     * *         Ports of different sizes can be connected.
     */
    val size: PortSize

    /**
     * @return the port that this port is connected to, or null if it is not connected.
     */
    val connectedPort: Port?

    /**
     * @return the machine this port belongs to.
     */
    val machine: Machine?

    /**
     * Attempts to connect this port to the specified port.
     * Throws an exception if the port could not be connected.
     */
    fun connect(otherPort: Port)

    /**
     * If this port is connected to another port, disconnects it.
     */
    fun disconnect()

    /**
     * @return true if this port is connected to some other port.
     */
    fun isConnected(): Boolean = connectedPort != null

    /**
     * @return true if this port can be connected to the specified port.
     */
    fun canConnect(otherPort: Port?): Boolean

    /**
     * Propagate any states of output ports to connected input ports.
     * @param time holds current simulation time and elapsed time since the last call to propagate.
     */
    fun propagate(time: Time)

    /**
     * Called once when the port is created.
     * @param machine the machine that the port belongs to.
     */
    fun init(machine: Machine)
}
