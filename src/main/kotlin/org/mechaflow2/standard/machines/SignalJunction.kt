package org.mechaflow2.standard.machines

import org.flowutils.time.Time
import org.mechaflow2.MachineBase
import org.mechaflow2.PortDirection
import org.mechaflow2.standard.ports.SignalPort


/**
 * Shares a single input signal with many outputs.
 */
class SignalJunction : MachineBase() {

    val input = addPort(SignalPort("Input", PortDirection.IN, description = "Input signal to distribute to the outputs"))
    val output1 = addPort(SignalPort("Output 1", PortDirection.OUT, description = "Receives the signal from the input"))
    val output2 = addPort(SignalPort("Output 2", PortDirection.OUT, description = "Receives the signal from the input"))
    val output3 = addPort(SignalPort("Output 3", PortDirection.OUT, description = "Receives the signal from the input"))
    val output4 = addPort(SignalPort("Output 4", PortDirection.OUT, description = "Receives the signal from the input"))
    val output5 = addPort(SignalPort("Output 5", PortDirection.OUT, description = "Receives the signal from the input"))

    override fun update(time: Time) {
        val value = input.value
        output1.value = value
        output2.value = value
        output3.value = value
        output4.value = value
        output5.value = value
    }
}
