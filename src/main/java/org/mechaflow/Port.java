package org.mechaflow;

import org.flowutils.time.Time;

/**
 * Connection available on a module.
 * Unique for each module instance.
 */
public interface Port {

    /*

    TODO: Could as well have a port keep track of all of these, instead of creating a ConnectorType that wraps them?
    TODO: Things like connector sizes or techs should be parametrizable for machines thou, so not something they should
          have to specify statically..
    TODO: So there should rather be factories for creating machine components based on specifications, and with methods to
          query the materials and knowledge, skills, and work required for building a certain parametrization.

    Connection directions:
    - Input
    - Output
    - Both

    Connection types:
      Information
        - Signal
          (- boolean)
          (- ranged (0.0 .. 1.0))
          (- counting (integer))
          - value (float)
        - Datagram (assembly of datatypes (numbers, booleans, text, lists, maps, datagrams),
                    following some specified datagram format (containing the names and types of the fields,
                    possibly allowing multiple versions))
      Power
        - Rotary power
        - Electric power
        - Hydraulic power (may fit under the fluid category)
        - Pneumatic power (may fit under the fluid category)
        - Other?
      Matter
        - Entities (or heaps of entities)
        - Loose matter(?) (in fixed volumes, one way)
        - Fluid (two way with pressure)
      Other?  E.g. mana/magic.

    Connector sizes
        2m ^ x, where x is an integer

    Connector techs
        - Wood, wrought iron, cast iron, machined steel, composites, etc..

    Connector models
        - Imperial standard, galactic standard, etc...
     */


    /**
     * @return user readable name/id of the port.
     */
    String getName();

    /**
     * @return the connection type this port has.
     */
    ConnectorType getConnectorType();

    /**
     * @return the direction of energy, matter or information transmission in this port.
     */
    PortDirection getDirection();

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
