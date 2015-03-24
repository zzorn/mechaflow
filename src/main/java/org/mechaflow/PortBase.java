package org.mechaflow;


import org.flowutils.Check;

import static org.flowutils.Check.notNull;

/**
 *
 */
public abstract class PortBase implements Port {

    private Port connectedPort;
    private Machine machine;
    private final String name;
    private final ConnectorType connectorType;
    private final PortDirection direction;

    /**
     * @param name user readable name/id of the port.
     * @param connectorType type of connection that can be connected to this port.
     * @param direction the direction of energy, matter or information transmission in this port.
     */
    protected PortBase(String name, ConnectorType connectorType, PortDirection direction) {
        Check.nonEmptyString(name, "name");
        notNull(connectorType, "connectionType");
        notNull(direction, "direction");

        this.name = name;
        this.connectorType = connectorType;
        this.direction = direction;
    }

    @Override public final String getName() {
        return name;
    }

    @Override public final ConnectorType getConnectorType() {
        return connectorType;
    }

    @Override public final PortDirection getDirection() {
        return direction;
    }

    @Override public final void init(Machine machine) {
        notNull(machine, "machine");
        if (this.machine != null) throw new IllegalStateException("The port has already been initialized!");

        this.machine = machine;
    }

    @Override public final Machine getMachine() {
        return machine;
    }

    @Override public final void connect(Port otherPort) {
        notNull(otherPort, "otherPort");

        // If already connected, no need to do anything
        if (connectedPort == otherPort) return;

        // Check that we can connect
        if (!canConnect(otherPort)) throw new IllegalArgumentException("Can not connect the port "+this+" to the port "+otherPort+".");

        // Disconnect existing connection
        disconnect();

        // Update connected port
        connectedPort = otherPort;

        // Let other port connect as well, if not already connected
        if (otherPort.getConnectedPort() != this) {
            otherPort.connect(this);
        }

        onConnected(otherPort);
    }

    @Override public final void disconnect() {
        if (connectedPort != null) {
            final Port previousConnectedPort = connectedPort;
            connectedPort = null;

            if (previousConnectedPort.isConnected()) {
                previousConnectedPort.disconnect();
            }

            onDisconnected(previousConnectedPort);
        }
    }

    @Override public final Port getConnectedPort() {
        return connectedPort;
    }

    @Override public final boolean isConnected() {
        return connectedPort != null;
    }

    @Override public final boolean canConnect(Port otherPort) {
        return otherPort != null &&
               otherPort != this &&
               direction.canConnect(otherPort.getDirection()) &&
               (otherPort.getConnectorType() == connectorType || connectorType.equals(otherPort.getConnectorType()));
    }

    /**
     * Called when connecting to another port.
     */
    protected void onConnected(Port otherPort) {
    }

    /**
     * Called when disconnecting from a port.
     */
    protected void onDisconnected(Port previousConnectedPort) {
    }


}
