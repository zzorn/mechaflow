package org.mechaflow.standard.ports;

import org.flowutils.time.Time;
import org.mechaflow.PortBase;
import org.mechaflow.PortDirection;

/**
 * Port for transmission of electric power
 */
public final class ElectricPort extends PortBase {

    // TODO: Information on maximum supported current and voltage, and handling of failure conditions

    private double voltage;
    private double outwardsCurrent;

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

                // Voltages should approach each other
                double averageV = 0.5 * (voltage + connectedPort.voltage);
                voltage = averageV;
                connectedPort.voltage = averageV;

                // Currents should be the same, but of opposite signs
                double averageCurrent = 0.5 * (outwardsCurrent - connectedPort.outwardsCurrent);
                outwardsCurrent = averageCurrent;
                connectedPort.outwardsCurrent = -averageCurrent;
            }
        }
        else {
            // Not connected, so keep the current at zero
            outwardsCurrent = 0;
        }
    }

    public double getVoltage() {
        return voltage;
    }

    public void setVoltage(double voltage) {
        this.voltage = voltage;
    }

    public double getOutwardsCurrent() {
        return outwardsCurrent;
    }

    public void setOutwardsCurrent(double outwardsCurrent) {
        this.outwardsCurrent = outwardsCurrent;
    }

}
