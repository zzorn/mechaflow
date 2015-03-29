package org.mechaflow.standard.machines;

import org.flowutils.time.Time;
import org.mechaflow.standard.StandardMachineBase;
import org.mechaflow.standard.ports.ElectricPort;
import org.mechaflow.standard.ports.SignalPort;

/**
 * Measures current, voltage, and possibly other properties of an electric conductor
 */
// TODO: Needs internal resistance so that it can update the current
public class ElectricMeter extends StandardMachineBase {

    public final ElectricPort a = electricPort("A");
    public final ElectricPort b = electricPort("B");
    public final SignalPort measuredCurrent = outputSignal("Measured current",
                                                           "The electrical current from a to b measured at this point, in amperes.");
    public final SignalPort measuredVoltage = outputSignal("Measured voltage", "The voltage at this point, in volts");

    @Override public void update(Time time) {
        double volts = 0.5 * (a.getVoltage() + b.getVoltage());
        double current = 0.5 * (b.getOutwardsCurrent() - a.getOutwardsCurrent());

        a.setVoltage(volts);
        b.setVoltage(volts);

        a.setOutwardsCurrent(-current);
        b.setOutwardsCurrent(current);

        measuredCurrent.set((float) current);
        measuredVoltage.set((float) volts);
    }
}
