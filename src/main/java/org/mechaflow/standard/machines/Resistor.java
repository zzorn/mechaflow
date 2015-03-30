package org.mechaflow.standard.machines;

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

        double chargeA = a.getCharge();
        double chargeB = b.getCharge();

        // Determine electric field over the resistor
        // Assuming parallel plates of equal size and distance with vacuum in between, the voltage between them is V = chargeDifference.
        double voltageDropFromAToB = chargeA - chargeB;

        // Determine current through the resistor (from a to b)
        double currentFromAToB = voltageDropFromAToB / resistance_Ohm;

        // Calculate amount of moved charge
        // Ampere = Coulomb / Second => Coulomb = Ampere * Seconds.
        double chargeMovedFromAToB = currentFromAToB * time.getLastStepDurationSeconds();

        // We can not move more charge than what we have at both ends, and we can only move the charge until it is balanced
        double mostMovableChargeFromAToB = 0.5 * (chargeA - chargeB);
        if (chargeMovedFromAToB >= 0) {
            chargeMovedFromAToB = Math.min(chargeMovedFromAToB, mostMovableChargeFromAToB);
        }
        else {
            chargeMovedFromAToB = Math.max(chargeMovedFromAToB, mostMovableChargeFromAToB);
        }

        // Move the charge
        a.changeCharge(-chargeMovedFromAToB);
        b.changeCharge(chargeMovedFromAToB);


        /*
        final double voltA = a.getVoltage();
        final double voltB = b.getVoltage();
        double oldVoltageDrop = voltA - voltB;
        double oldCurrent = 0.5 * (b.getOutwardsCurrent() - a.getOutwardsCurrent());

        // TODO: This breaks with large resistance values mixed with small, leading to oscillating current and breakout to + or - infinite current.
        // TODO: Try moving charges instead?

        final double currentInterpolationFactor = 0.8;
        final double voltageInterpolationFactor = 0.2;

        // Update current and voltage drop
        double newCurrent = oldCurrent;
        double newVoltageDrop = oldVoltageDrop;

        for (int i = 0; i < 100; i++) {
            newCurrent = MathUtils.mix(currentInterpolationFactor, newCurrent, newVoltageDrop / resistance_Ohm);
            newVoltageDrop = MathUtils.mix(voltageInterpolationFactor, newVoltageDrop, newCurrent * resistance_Ohm);
        }

        // Update current to ports
        a.setOutwardsCurrent(-newCurrent);
        b.setOutwardsCurrent(newCurrent);

        // Update volts to ports
        double averageVolts = 0.5 * (voltA + voltB);
        double halfNewVoltageDrop = 0.5 * newVoltageDrop;
        if (voltA < voltB) {
            a.setVoltage(averageVolts - halfNewVoltageDrop);
            b.setVoltage(averageVolts + halfNewVoltageDrop);
        }
        else {
            a.setVoltage(averageVolts + halfNewVoltageDrop);
            b.setVoltage(averageVolts - halfNewVoltageDrop);
        }
        */
    }

    public double getResistance_Ohm() {
        return resistance_Ohm;
    }

    public void setResistance_Ohm(double resistance_Ohm) {
        this.resistance_Ohm = resistance_Ohm;
    }
}
