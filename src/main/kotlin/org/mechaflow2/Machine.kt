package org.mechaflow2

import org.flowutils.time.Time
import org.flowutils.updating.Updating
import org.mechaflow.standard.heat.Heatable

/**
 * A machine or part of a machine.
 * Has ports that can be connected to ports on other Machines.
 */
interface Machine: Heatable, Updating {

    /**
     * The ports available on this Machine.
     */
    val ports: List<Port>

    /**
     * Propagate updates from output ports to connected input ports.
     * @param time current game time and the duration since the last update.
     */
    open fun propagate(time: Time) {
        for (port in ports) {
            port.propagate(time)
        }
    }

    /**
     * Disconnects all ports.
     */
    fun disconnectAll() {
        for (port in ports) {
            port.disconnect()
        }
    }

}
