package org.mechaflow.standard;

import org.flowutils.time.Time;
import org.mechaflow.PortBase;
import org.mechaflow.PortDirection;

/**
 *
 */
// NOTE: Similar to an item port, has a queue.  Maybe share code?
public final class DatagramPort extends PortBase {

    // TODO: Queue of datagrams, of some maximum size?

    public DatagramPort(String name, PortDirection direction) {
        this(name, direction, DEFAULT_SMALL_SIZE_GAUGE);
    }

    public DatagramPort(String name, PortDirection direction, int sizeGauge) {
        this(name, direction, sizeGauge, sizeGauge);
    }

    public DatagramPort(String name, PortDirection direction, int widthGauge, int heightGauge) {
        super(name, direction, widthGauge, heightGauge);
        if (direction == PortDirection.INOUT) throw new IllegalArgumentException("A datagram port must be either an input or output port, INOUT datagram ports are not supported");
    }

    @Override public void update(Time time) {
        // TODO: Implement

    }

    @Override public void propagate(Time time) {
        // TODO: Implement
        // TODO: Move datagrams to receiver, all at once, or with some delay per datagram?
    }

    // TODO: Methods to queue datagrams and to check queue size / if datagrams can be queued

}
