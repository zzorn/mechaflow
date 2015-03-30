package org.mechaflow.standard.machines;

import org.flowutils.time.Time;
import org.mechaflow.standard.StandardMachineBase;
import org.mechaflow.standard.ports.ElectricPort;
import org.mechaflow.standard.ports.SignalPort;

/**
 * Measures current passing from the a to the b port.
 */
public class CurrentMeter extends StandardMachineBase {

    public final ElectricPort a = electricPort("A");
    public final ElectricPort b = electricPort("B");
    public final SignalPort measuredCurrent = outputSignal("Measured current",
                                                           "The electrical current from a to b measured at this point, in amperes.");

    private double internalResistance = 0.01;

    @Override public void update(Time time) {

        // Move charge through the meter, and get the current
        double current = conductChargeThroughResistor(a, b, internalResistance, time.getLastStepDurationSeconds(), this);

        // Notify the listener.
        measuredCurrent.set((float) current);
    }
}
