package org.mechaflow.standard.ports;

import org.flowutils.time.Time;
import org.mechaflow.PortBase;
import org.mechaflow.PortDirection;

/**
 * Port for transmission of rotary power
 */
public final class RotaryPort extends PortBase {

    // LATER: Information on maximum supported torque and velocity, and handling of failure conditions


    private double torque; // Sum of torques from inside this machine and from the connected port
    private double angularVelocity; // Update based on previous state and current torques

    // TODO: Inertia
    // TODO: angular momentum is what we have to retain in the system (angular velocity * inertia)

    public RotaryPort(String name) {
        this(name, DEFAULT_MEDIUM_SIZE_GAUGE);
    }

    public RotaryPort(String name, int sizeGauge) {
        this(name, sizeGauge, sizeGauge);
    }

    public RotaryPort(String name, int widthGauge, int heightGauge) {
        super(name, PortDirection.INOUT, widthGauge, heightGauge);
    }

    @Override public void propagate(Time time) {
        // TODO: Implement

    }
}
