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

    @Override public void update(Time time) {

        double chargeA = a.getCharge();
        double chargeB = b.getCharge();

        double chargeMovedFromAToB = 0.5 * (chargeA - chargeB);

        // Current = A = MovedCharge / Second
        // This may be infinite if the time step is zero.  To get around that, add some internal resistance to the ammeter.
        double current =  chargeMovedFromAToB / time.getSecondsSinceLastStep();

        // Move the charge
        a.changeCharge(-chargeMovedFromAToB);
        b.changeCharge(chargeMovedFromAToB);

        // Notify the listener.
        measuredCurrent.set((float) current);
    }
}
