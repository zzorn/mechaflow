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

        // If the charges are already equal, we don't have to do anything else, as we can't move any charge in that case
        if (chargeA != chargeB) {
            // Determine electric field over the resistor
            // Assuming parallel plates of equal size and distance with vacuum in between, the voltage between them is V = chargeDifference.
            double voltageDropFromAToB = (chargeA - chargeB);

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
        }


    }

    public double getResistance_Ohm() {
        return resistance_Ohm;
    }

    public void setResistance_Ohm(double resistance_Ohm) {
        this.resistance_Ohm = resistance_Ohm;
    }
}
