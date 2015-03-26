package org.mechaflow.standard;

import org.flowutils.time.Time;
import org.mechaflow.PortBase;
import org.mechaflow.PortDirection;

/**
 * Port that propagates signals from input to output ports.
 */
public final class SignalPort extends PortBase {

    private float signal;

    public SignalPort(String name, PortDirection direction) {
        this(name, direction, DEFAULT_SMALL_SIZE_GAUGE);
    }

    public SignalPort(String name, PortDirection direction, int sizeGauge) {
        this(name, direction, sizeGauge, sizeGauge);
    }

    public SignalPort(String name, PortDirection direction, int widthGauge, int heightGauge) {
        super(name, direction, widthGauge, heightGauge);
        if (direction == PortDirection.INOUT) throw new IllegalArgumentException("A signal port must be either an input or output port, INOUT signal ports are not supported");
    }

    @Override public void update(Time time) {
        // TODO: Implement

    }

    @Override public void propagate(Time time) {
        // Propagate our signal to the connected port
        if (getDirection() == PortDirection.OUT && isConnected()) {
            ((SignalPort) getConnectedPort()).setSignal(signal);
        }
    }

    public float getSignal() {
        return signal;
    }

    public void setSignal(float signal) {
        this.signal = signal;
    }
}
