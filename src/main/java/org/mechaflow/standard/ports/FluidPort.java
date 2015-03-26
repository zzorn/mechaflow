package org.mechaflow.standard.ports;

import org.flowutils.time.Time;
import org.mechaflow.PortBase;
import org.mechaflow.PortDirection;

/**
 *
 */
public final class FluidPort extends PortBase {

    // TODO: Information on maximum supported pressure and flow, and handling of failure conditions

    // TODO: Also keep track of fluid composition

    private double pressure;
    private double flow;

    public FluidPort(String name) {
        this(name, DEFAULT_MEDIUM_SIZE_GAUGE);
    }

    public FluidPort(String name, int sizeGauge) {
        this(name, sizeGauge, sizeGauge);
    }

    public FluidPort(String name, int widthGauge, int heightGauge) {
        super(name, PortDirection.INOUT, widthGauge, heightGauge);
    }

    @Override public void update(Time time) {
        // TODO: Implement

    }

    @Override public void propagate(Time time) {
        // TODO: Implement

    }

    // TODO: Method to get/set fluid composition at inlet location

    // TODO: Maybe a queue of fluids of a sort, or a connected fluid container (the pipe into the machine)?
}
