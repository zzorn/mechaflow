package org.mechaflow.standard.ports;

import org.flowutils.time.Time;
import org.mechaflow.PortBase;
import org.mechaflow.PortDirection;

/**
 * Port for transmission of electric power
 */
// TODO: Drop Electricity simulation, instead just add support for power transmission (through electricity or rotation, specify the
// TODO  transfer voltages and the available power (or transfer RPM and available power).
// TODO  Certain applications may require a certain voltage or RPM level, in which case other components may be used to transform the
// TODO  voltage or RPM level.  Each component using (or providing) some power removes (or adds) it to the power bus.
// TODO  Excess energy may be distributed with some weights?
// TODO  Producers could record how much they offer to produce, and then not produce the part that was not needed
// TODO  In some cases the power is produced in any case, and needs to be sunk somewhere?
// NOTE: Signals can be used instead of electricity to transport information and do computation that electricity would
// NOTE  normally have been used for
// NOTE: If we use power ports, we only need to update things when a state changes, speeding up processing considerably
public final class ElectricPort extends PortBase {

    // TODO: Information on maximum supported current and voltage, and handling of failure conditions

    private double charge; // In coulomb

    public ElectricPort(String name) {
        this(name, null);
    }

    public ElectricPort(String name, String description) {
        this(name, description, DEFAULT_SMALL_SIZE_GAUGE);
    }

    public ElectricPort(String name, String description, int sizeGauge) {
        this(name, description, sizeGauge, sizeGauge);
    }

    public ElectricPort(String name, String description, int widthGauge, int heightGauge) {
        super(name, PortDirection.INOUT, description, widthGauge, heightGauge);
    }

    @Override public void propagate(Time time) {
        if (isConnected()) {
            // One of the connected ports handles the propagation, use the one with the smaller hashcode
            // NOTE: If the hashcode happens to be the same it will just do it twice, but that should not be an issue.
            if (hashCode() <= getConnectedPort().hashCode()) {
                final ElectricPort connectedPort = (ElectricPort) getConnectedPort();

                // Distribute charge
                double averageCharge = 0.5 * (charge + connectedPort.charge);
                charge = averageCharge;
                connectedPort.charge = averageCharge;
            }
        }
    }

    /**
     * @return the charge in coulomb currently located in this port.
     */
    public double getCharge() {
        return charge;
    }

    /**
     * @param charge charge to set the port to.
     */
    public void setCharge(double charge) {
        this.charge = charge;
    }

    /**
     * @param chargeDelta change to add to the charge in the port.
     */
    public void changeCharge(double chargeDelta) {
        this.charge += chargeDelta;
    }
}
