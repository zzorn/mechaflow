package org.mechaflow.standard.machines;

import org.flowutils.time.Time;
import org.mechaflow.standard.StandardMachineBase;
import org.mechaflow.standard.ports.ElectricPort;

/**
 *
 */
public class Generator extends StandardMachineBase {

    public final ElectricPort plusPole = electricPort("Plus pole");
    public final ElectricPort minusPole = electricPort("Minus pole");

    // TODO: Add rotary port for shaft

    // TODO: Temp values for testing
    private double volt = 5;
    private double internalResistance_Ohm = 0.5;

    private double maxCurrent_A = volt / internalResistance_Ohm;

    @Override public void update(Time time) {
        double averageVolt = 0.5 * (minusPole.getVoltage() + plusPole.getVoltage());

        plusPole.setVoltage(averageVolt + 0.5 * volt);
        minusPole.setVoltage(averageVolt - 0.5 * volt);

        double current = 0.5 * (plusPole.getOutwardsCurrent() - minusPole.getOutwardsCurrent());
        if (current > maxCurrent_A) {
            current = maxCurrent_A;
        }
        else if (current < -maxCurrent_A) {
            current = -maxCurrent_A;
        }

        plusPole.setOutwardsCurrent(current);
        minusPole.setOutwardsCurrent(-current);

    }
}
