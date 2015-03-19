package org.mechaflow;

import org.flowutils.time.Time;

import java.util.List;

/**
 * A machine or part of a machine.
 * Has ports that can be connected to ports on other Machines.
 */
public interface Machine {

    /**
     * @return the ports available on this Machine.
     */
    List<Port> getPorts();

    /**
     * Update the components internal state and output ports based on its internal state and input ports (and elapsed time since the last update).
     * @param time current game time and the duration since the last update.
     */
    void update(Time time);

    /**
     * Propagate updates from output ports to connected input ports.
     * @param time current game time and the duration since the last update.
     */
    void propagate(Time time);

    /**
     * Disconnects all ports.
     */
    void disconnectAll();
}
