package org.mechaflow.standard;

import org.mechaflow.MachineBase;
import org.mechaflow.PortDirection;
import org.mechaflow.standard.ports.ElectricPort;
import org.mechaflow.standard.heat.Heatable;
import org.mechaflow.standard.ports.SignalPort;

/**
 * Provides convenience methods for adding standard ports.
 */
public abstract class StandardMachineBase extends MachineBase {

    public static final double VACUUM_PERMITTIVITY = 8.854187817 * Math.pow(10, -12); // In Farads/meter

    // Signals

    protected final SignalPort inputSignal(String name) {
        return inputSignal(name, null);
    }

    protected final SignalPort inputSignal(String name, String description) {
        return inputSignal(name, 0f, description);
    }

    protected final SignalPort inputSignal(String name, float initialValue) {
        return inputSignal(name, initialValue, null);
    }

    protected final SignalPort inputSignal(String name, float initialValue, String description) {
        return addPort(new SignalPort(name, PortDirection.IN, initialValue, description));
    }

    protected final SignalPort outputSignal(String name) {
        return outputSignal(name, null);
    }

    protected final SignalPort outputSignal(String name, String description) {
        return addPort(new SignalPort(name, PortDirection.OUT, description));
    }


    // Electricity
    protected final ElectricPort electricPort(String name) {
        return electricPort(name, null);
    }

    protected final ElectricPort electricPort(String name, String description) {
        return addPort(new ElectricPort(name, description));
    }

    /**
     * Discharges charges between the two provided ports through a resistance of the specified magnitude over the specified time.
     * Updates the charges at the two provided ports.
     * Heats the provided heatable with the excess energy.
     * @return the current flowing from port a to port b, in amperes.  Negative if flowing from port b to port a.
     */
    protected static double conductChargeThroughResistor(final ElectricPort electricPortA,
                                                         final ElectricPort electricPortB,
                                                         final double resistance,
                                                         final double seconds,
                                                         Heatable heatable) {
        final double chargeA = electricPortA.getCharge();
        final double chargeB = electricPortB.getCharge();

        // If the charges are already equal, we don't have to do anything else, as we can't move any charge in that case
        if (chargeA != chargeB && seconds > 0) {

            /*
            We know:
            - The charge at both conductors connecting to the resistor
            - The resistance of the resistor in ohms

            We want to calculate:
            - The voltage drop over the resistor
              Which gives us
              - The current through the resistor (current = voltageDrop / resistance)
                Which gives us:
                - The new charges at the connected conductors (charge delta = current / time)
                - The energy converted to heat (heat energy = current^2 * resistance)

            Calculation:
              voltageDrop = distance * chargeDiff / (permittivity * area)

              Assume area and distance approach some small similar value (e.g. 1mm and 1mm^2).  Then:
              voltageDrop = chargeDiff / permittivity

              permittivity is close to vacuumPermittivity for e.g. metal film resistors

              Thus
              voltageDrop = chargeDifference / vacuumPermittivity

             */


            // Determine electric field over the resistor
            // Assuming parallel plates of equal size and distance with generic material
            // with permittivity close to vacuum permittivity in between
            final double voltageDropFromAToB = (chargeA - chargeB) / VACUUM_PERMITTIVITY;
            System.out.println("voltageDropFromAToB = " + voltageDropFromAToB);

            // We can not move more charge than what we have at both ends, and we can only move the charge until it is balanced
            final double mostMovableChargeFromAToB = 0.5 * (chargeA - chargeB);

            // Determine current through the resistor (from a to b)
            final double currentFromAToB;
            if (resistance <= 0) {
                // No resistance, move charges to balance them.
                currentFromAToB = mostMovableChargeFromAToB;
            }
            else {
                currentFromAToB = voltageDropFromAToB / resistance;
            }

            // Calculate amount of moved charge
            // Ampere = Coulomb / Second => Coulomb = Ampere * Seconds.
            double chargeMovedFromAToB = currentFromAToB * seconds;

            // Clamp current to the max that can be moved
            if (chargeMovedFromAToB >= 0) {
                chargeMovedFromAToB = Math.min(chargeMovedFromAToB, mostMovableChargeFromAToB);
            }
            else {
                chargeMovedFromAToB = Math.max(chargeMovedFromAToB, mostMovableChargeFromAToB);
            }

            // Move the charge
            electricPortA.changeCharge(-chargeMovedFromAToB);
            electricPortB.changeCharge(chargeMovedFromAToB);

            // Determine heating power
            double actualCurrent = chargeMovedFromAToB / seconds;
            // Heat energy = P * seconds, P = I^2 * R.
            heatable.addHeatEnergy(resistance * actualCurrent * actualCurrent * seconds);

            return actualCurrent;
        }
        else {
            // No charge moved, so no current
            return 0;
        }
    }
}
