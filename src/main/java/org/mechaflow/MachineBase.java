package org.mechaflow;

import org.flowutils.Check;
import org.flowutils.time.Time;
import org.mechaflow.standard.heat.DefaultHeatable;

import java.util.ArrayList;
import java.util.List;

import static org.flowutils.Check.notNull;

/**
 *
 */
public abstract class MachineBase extends DefaultHeatable implements Machine {

    private List<Port> ports;

    @Override public final List<Port> getPorts() {
        return ports;
    }

    @Override public final void propagate(Time time) {
        for (Port port : ports) {
            port.propagate(time);
        }
    }

    @Override public final void disconnectAll() {
        for (Port port : ports) {
            port.disconnect();
        }
    }

    /**
     * @param port port to add to the machine.
     * @return the added port, for assigning to public fields or similar.
     */
    protected final <P extends Port> P addPort(P port) {
        notNull(port, "port");

        // Lazy init ports list, as initialization order of it may be later than ports added in fields in descendant classes.
        if (ports == null) {
            ports = new ArrayList<>(5);
        }

        Check.notContained(port, ports, "ports");

        port.init(this);

        ports.add(port);

        return port;
    }
}
