package org.mechaflow.standard.machines;

import org.flowutils.MathUtils;
import org.flowutils.time.Time;
import org.mechaflow.standard.StandardMachineBase;
import org.mechaflow.standard.ports.ElectricPort;

import static org.flowutils.MathUtils.*;

/**
 *
 */
public class Generator extends StandardMachineBase {

    public final ElectricPort plusPole = electricPort("Plus pole");
    public final ElectricPort minusPole = electricPort("Minus pole");

    // TODO: Add rotary port for shaft

    // TODO: Temp values for testing
    private double volt = 5;
    private double maxCurrent_A = 10;

    @Override public void update(Time time) {

        // Charge up the opposite poles, until the voltage over the generator is the drive voltage, but at most by max current

        double chargeOnPlus = plusPole.getCharge();
        double chargeOnMinus = minusPole.getCharge();

        // Assuming parallel plates of equal size and distance with vacuum in between, the voltage between them is V = chargeDifference.
        double voltageDifference = chargeOnPlus - chargeOnMinus;
        double targetChargeDifference = volt;
        double targetCharging = targetChargeDifference - voltageDifference;

        // Determine charge produced
        double producedCharge = clamp(maxCurrent_A * time.getSecondsSinceLastStep(), 0, targetCharging);

        // Smooth it a bit (we could use some better integration than euler here maybe?)
//        producedCharge *= 0.8;

        // Update poles
        plusPole.changeCharge(0.5 * producedCharge);
        minusPole.changeCharge(-0.5 * producedCharge);
    }
}
