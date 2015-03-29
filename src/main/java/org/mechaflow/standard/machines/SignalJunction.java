package org.mechaflow.standard.machines;

import org.flowutils.time.Time;
import org.mechaflow.standard.StandardMachineBase;
import org.mechaflow.standard.ports.SignalPort;

/**
 * Shares a single input signal with many outputs.
 */
public final class SignalJunction extends StandardMachineBase {

    public final SignalPort input = inputSignal("Input", "Input signal to distribute to the outputs");
    public final SignalPort output1 = outputSignal("Output 1", "Receives the signal from the input");
    public final SignalPort output2 = outputSignal("Output 2", "Receives the signal from the input");
    public final SignalPort output3 = outputSignal("Output 3", "Receives the signal from the input");
    public final SignalPort output4 = outputSignal("Output 4", "Receives the signal from the input");
    public final SignalPort output5 = outputSignal("Output 5", "Receives the signal from the input");

    @Override public void update(Time time) {
        final float value = input.get();
        output1.set(value);
        output2.set(value);
        output3.set(value);
        output4.set(value);
        output5.set(value);
    }
}
