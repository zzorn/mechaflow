package org.mechaflow.standard.machines;

import org.flowutils.MathUtils;
import org.flowutils.time.Time;
import org.mechaflow.standard.StandardMachineBase;
import org.mechaflow.standard.ports.ElectricPort;

/**
 *
 */
public class Resistor extends StandardMachineBase {

    public final ElectricPort a = electricPort("A");
    public final ElectricPort b = electricPort("B");

    private double resistance_Ohm = 100;

    public Resistor() {
    }

    public Resistor(double resistance_Ohm) {
        this.resistance_Ohm = resistance_Ohm;
    }

    @Override public void update(Time time) {

        final double voltA = a.getVoltage();
        final double voltB = b.getVoltage();

        double voltageDrop = voltA - voltB;

        double newCurrent = voltageDrop / resistance_Ohm;

        double oldCurrent = 0.5 * (b.getOutwardsCurrent() - a.getOutwardsCurrent());

        // TODO: This breaks with large resistance values mixed with small, leading to oscillating current and breakout to + or - infinite current.
        // TODO: Try moving charges instead?

        // Update current
        newCurrent = MathUtils.mix(0.1, oldCurrent, newCurrent);

        a.setOutwardsCurrent(-newCurrent);
        b.setOutwardsCurrent(newCurrent);

        // Update volts
        double halfNewVoltageDrop = 0.5 * (newCurrent * resistance_Ohm);
        double averageVolts = 0.5 * (voltA + voltB);
        if (voltA < voltB) {
            a.setVoltage(averageVolts - halfNewVoltageDrop);
            b.setVoltage(averageVolts + halfNewVoltageDrop);
        }
        else {
            a.setVoltage(averageVolts + halfNewVoltageDrop);
            b.setVoltage(averageVolts - halfNewVoltageDrop);
        }
    }

    public double getResistance_Ohm() {
        return resistance_Ohm;
    }

    public void setResistance_Ohm(double resistance_Ohm) {
        this.resistance_Ohm = resistance_Ohm;
    }
}
