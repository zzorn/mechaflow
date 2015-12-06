package org.mechaflow2.standard.machines

import org.flowutils.time.Time
import org.mechaflow2.standard.StandardMachineBase


/**
 * Shares a single input signal with many outputs.
 */
class SignalJunction : StandardMachineBase() {

    val input = inputSignal("Input", "Input signal to distribute to the outputs")
    val output1 = outputSignal("Output 1", "Receives the signal from the input")
    val output2 = outputSignal("Output 2", "Receives the signal from the input")
    val output3 = outputSignal("Output 3", "Receives the signal from the input")
    val output4 = outputSignal("Output 4", "Receives the signal from the input")
    val output5 = outputSignal("Output 5", "Receives the signal from the input")

    override fun update(time: Time) {
        val inputValue = input.value
        output1.value = inputValue
        output2.value = inputValue
        output3.value = inputValue
        output4.value = inputValue
        output5.value = inputValue
    }
}
