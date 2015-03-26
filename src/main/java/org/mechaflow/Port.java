package org.mechaflow;

import org.flowutils.time.Time;

/**
 * Connection available on a module.
 * Unique for each module instance.
 */
public interface Port {

    /*

    TODO: Things like connector sizes or techs should be parametrizable for machines thou, so not something they should
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
     * @return user readable name/id of the port.
     */
    String getName();

    /**
     * @return the direction of energy, matter or information transmission in this port.
     */
    PortDirection getDirection();

    /**
     * @return width of the port, expressed as 2^getWidth() meters.
     *         Ports of different sizes can be connected.
     */
    int getWidthGauge();

    /**
     * @return height of the port, expressed as 2^getHeight() meters.
     *         Ports of different sizes can be connected.
     */
    int getHeightGauge();

    /**
     * @return width of the port, in meters.
     */
    double getWidthMeters();

    /**
     * @return height of the port, in meters.
     */
    double getHeightMeters();

    /**
     * Attempts to connect this port to the specified port.
     * Throws an exception if the port could not be connected.
     */
    void connect(Port otherPort);

    /**
     * If this port is connected to another port, disconnects it.
     */
    void disconnect();

    /**
     * @return the port that this port is connected to.
     */
    Port getConnectedPort();

    /**
     * @return true if this port is connected to some other port.
     */
    boolean isConnected();

    /**
     * @return true if this port can be connected to the specified port.
     */
    boolean canConnect(Port otherPort);

    /**
     * Calculates any states in the port, and does other logic checking, such as failure checks.
     * @param time holds current simulation time and elapsed time since the last call to update.
     */
    void update(Time time);

    /**
     * Propagate any states of output ports to connected input ports.
     * @param time holds current simulation time and elapsed time since the last call to propagate.
     */
    void propagate(Time time);

    /**
     * @return the machine this port belongs to.
     */
    Machine getMachine();

    /**
     * Called once when the port is created.
     * @param machine the machine that the port belongs to.
     */
    void init(Machine machine);
}
