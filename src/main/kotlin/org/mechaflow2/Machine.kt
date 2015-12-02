package org.mechaflow2

import org.flowutils.time.Time
import org.flowutils.updating.Updating
import org.mechaflow.Port
import org.mechaflow.standard.heat.Heatable

/**
 * A machine or part of a machine.
 * Has ports that can be connected to ports on other Machines.
 */
interface Machine: Heatable, Updating {

    /**
     * @return the ports available on this Machine.
     */
    fun getPorts(): List<Port>

    /**
     * Propagate updates from output ports to connected input ports.
     * @param time current game time and the duration since the last update.
     */
    fun propagate(time: Time)

    /**
     * Disconnects all ports.
     */
    fun disconnectAll() {
        for (port in getPorts()) {
            port.disconnect()
        }
    }

}
