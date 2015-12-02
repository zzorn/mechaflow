package org.mechaflow2.standard.ports

import org.flowutils.time.Time
import org.mechaflow2.PortBase
import org.mechaflow2.PortDirection
import org.mechaflow2.PortSize


/**
 * Port that propagates signals from input to output ports.
 */
class SignalPort(name: String,
                 direction: PortDirection = PortDirection.IN,
                 val defaultValue: Double = 0.0,
                 val range: SignalRange = SignalRange.UNLIMITED,
                 description: String? = null,
                 size: PortSize = PortSize.MEDIUM) :
        PortBase(name, direction, description, size) {

    var value: Double = defaultValue
        set(newValue) {
            value = range.clamp(newValue)
        }

    init {
        if (direction == PortDirection.INOUT) {
            throw IllegalArgumentException(
                    "A signal port must be either an input or output port, INOUT signal ports are not supported")
        }
    }

    override fun propagate(time: Time) {
        // Propagate our signal to the connected port
        if (direction == PortDirection.OUT && isConnected()) {
            (connectedPort as SignalPort).value = this.value
        }
    }

    val isHigh: Boolean
        get() = value >= HIGH_LEVEL

    val isLow: Boolean
        get() = value < HIGH_LEVEL

    companion object {
        const val HIGH_LEVEL = 0.5
    }

}
