package org.mechaflow.standard.machines;

import org.flowutils.time.Time;
import org.mechaflow.standard.StandardMachineBase;
import org.mechaflow.standard.ports.ElectricPort;
import org.mechaflow.standard.ports.SignalPort;

/**
 * Measures voltage between a and b.
 */
public class VoltMeter extends StandardMachineBase {

    public final ElectricPort a = electricPort("A");
    public final ElectricPort b = electricPort("B");
    public final SignalPort measuredVoltage = outputSignal("Measured voltage",
                                                           "The electrical voltage difference between b and a, in volt.");

    private double internalResistance = 1000000;

    @Override public void update(Time time) {

        // Move some current through the meter
        double current = conductChargeThroughResistor(a, b, internalResistance, time.getLastStepDurationSeconds(), this);

        // TODO: Determine voltage based on charge
        double voltageDiff = a.getCharge() - b.getCharge();

        // Notify the listener.
        measuredVoltage.set((float) voltageDiff);
    }
}
