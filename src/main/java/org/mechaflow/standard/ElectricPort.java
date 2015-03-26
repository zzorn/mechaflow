package org.mechaflow.standard;

import org.flowutils.time.Time;
import org.mechaflow.PortBase;
import org.mechaflow.PortDirection;

/**
 * Port for transmission of electric power
 */
public final class ElectricPort extends PortBase {

    // TODO: Information on maximum supported current and voltage, and handling of failure conditions

    private double voltage; // Update based on voltage in machine and in the connected port
    private double current; // Update based on previous state (if there is some induction?) and voltages

    public ElectricPort(String name) {
        this(name, DEFAULT_SMALL_SIZE_GAUGE);
    }

    public ElectricPort(String name, int sizeGauge) {
        this(name, sizeGauge, sizeGauge);
    }

    public ElectricPort(String name, int widthGauge, int heightGauge) {
        super(name, PortDirection.INOUT, widthGauge, heightGauge);
    }

    @Override public void update(Time time) {
        // TODO: Implement

    }

    @Override public void propagate(Time time) {
        // TODO: Implement

    }

}
