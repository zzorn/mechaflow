package org.mechaflow.standard;

import org.flowutils.time.Time;
import org.mechaflow.PortBase;
import org.mechaflow.PortDirection;

/**
 * A port for transporting items between machines, e.g. a conveyor belt.
 */
// NOTE: Similar to a datagram port, has a queue.  Maybe share code?
public final class ItemPort extends PortBase {

    // TODO: Queue of entities

    public ItemPort(String name, PortDirection direction) {
        this(name, direction, DEFAULT_LARGE_SIZE_GAUGE);
    }

    public ItemPort(String name, PortDirection direction, int sizeGauge) {
        this(name, direction, sizeGauge, sizeGauge);
    }

    public ItemPort(String name, PortDirection direction, int widthGauge, int heightGauge) {
        super(name, direction, widthGauge, heightGauge);
        if (direction == PortDirection.INOUT) throw new IllegalArgumentException("An item port must be either an input or output port, INOUT item ports are not supported");
    }

    @Override public void update(Time time) {
        // TODO: Implement

    }

    @Override public void propagate(Time time) {
        // TODO: Implement
        // TODO: Move some entities per time unit to receiver, depending on entity size and port movement speed
    }

    // TODO: Methods to queue entities and to check queue size / if entities can be queued
}
