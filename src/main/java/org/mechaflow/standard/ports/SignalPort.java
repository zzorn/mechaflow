package org.mechaflow.standard.ports;

import org.flowutils.time.Time;
import org.mechaflow.PortBase;
import org.mechaflow.PortDirection;

/**
 * Port that propagates signals from input to output ports.
 */
public final class SignalPort extends PortBase {

    public static final float HIGH_LEVEL = 0.5f;

    private float value;

    public SignalPort(String name, PortDirection direction) {
        this(name, direction, null);
    }

    public SignalPort(String name, PortDirection direction, String description) {
        this(name, direction, description, DEFAULT_SMALL_SIZE_GAUGE);
    }

    public SignalPort(String name, PortDirection direction, float defaultValue, String description) {
        this(name, direction, description, DEFAULT_SMALL_SIZE_GAUGE);
        set(defaultValue);
    }

    public SignalPort(String name, PortDirection direction, String description, int sizeGauge) {
        this(name, direction, description, sizeGauge, sizeGauge);
    }

    public SignalPort(String name, PortDirection direction, String description, int widthGauge, int heightGauge) {
        super(name, direction, description, widthGauge, heightGauge);
        if (direction == PortDirection.INOUT) throw new IllegalArgumentException("A signal port must be either an input or output port, INOUT signal ports are not supported");
    }

    @Override public void propagate(Time time) {
        // Propagate our signal to the connected port
        if (getDirection() == PortDirection.OUT && isConnected()) {
            ((SignalPort) getConnectedPort()).set(value);
        }
    }

    public float get() {
        return value;
    }

    public void set(float value) {
        this.value = value;
    }

    public final boolean isHigh() {
        return value >= HIGH_LEVEL;
    }

    public final boolean isLow() {
        return value < HIGH_LEVEL;
    }

}
