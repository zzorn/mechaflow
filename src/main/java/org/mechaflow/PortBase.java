package org.mechaflow;


import org.flowutils.Check;

import static org.flowutils.Check.notNull;

/**
 *
 */
public abstract class PortBase implements Port {

    protected static final int DEFAULT_LARGE_SIZE_GAUGE = 0;
    protected static final int DEFAULT_MEDIUM_SIZE_GAUGE = -2;
    protected static final int DEFAULT_SMALL_SIZE_GAUGE = -4;
    private Port connectedPort;

    private Machine machine;
    private final String name;
    private final String description;
    private final PortDirection direction;
    private int widthGauge;
    private int heightGauge;

    /**
     * Creates a new port with a default width and height of size gauge 0 (1 meter).
     *
     * @param name user readable name/id of the port.
     * @param direction the direction of energy, matter or information transmission in this port.
     */
    protected PortBase(String name, PortDirection direction) {
        this(name, direction, DEFAULT_MEDIUM_SIZE_GAUGE);
    }

    /**
     * Creates a new port with a default width and height of size gauge 0 (1 meter).
     *
     * @param name user readable name/id of the port.
     * @param description user readable english description of the port, or null if no description is provided.
     * @param direction the direction of energy, matter or information transmission in this port.
     */
    protected PortBase(String name, String description, PortDirection direction) {
        this(name, direction, description, DEFAULT_MEDIUM_SIZE_GAUGE, DEFAULT_MEDIUM_SIZE_GAUGE);
    }

    /**
     * Creates a new port.
     *
     * @param name user readable name/id of the port.
     * @param direction the direction of energy, matter or information transmission in this port.
     * @param sizeGauge width and height class of the port.  Size fits inside 2 ^ sizeGauge meters per side.
     */
    protected PortBase(String name, PortDirection direction, int sizeGauge) {
        this(name, direction, null, sizeGauge, sizeGauge);
    }

    /**
     * Creates a new port.
     *
     * @param name user readable name/id of the port.
     * @param direction the direction of energy, matter or information transmission in this port.
     * @param widthGauge width class of the port.  Size fits inside 2 ^ widthGauge meters.
     * @param heightGauge height class of the port.  Size fits inside 2 ^ heightGauge meters.
     */
    protected PortBase(String name, PortDirection direction, int widthGauge, int heightGauge) {
        this(name, direction, null, widthGauge, heightGauge);
    }

    /**
     * Creates a new port.
     *
     * @param name user readable name/id of the port.
     * @param description user readable english description of the port, or null if no description is provided.
     * @param direction the direction of energy, matter or information transmission in this port.
     * @param widthGauge width class of the port.  Size fits inside 2 ^ widthGauge meters.
     * @param heightGauge height class of the port.  Size fits inside 2 ^ heightGauge meters.
     */
    protected PortBase(String name, PortDirection direction, String description, int widthGauge, int heightGauge) {
        Check.nonEmptyString(name, "name");
        notNull(direction, "direction");

        this.name = name;
        this.description = description;
        this.direction = direction;
        this.widthGauge = widthGauge;
        this.heightGauge = heightGauge;
    }

    @Override public final String getName() {
        return name;
    }

    @Override public String getDescription() {
        return description;
    }

    @Override public final PortDirection getDirection() {
        return direction;
    }

    public final int getWidthGauge() {
        return widthGauge;
    }

    public final void setWidthGauge(int widthGauge) {
        this.widthGauge = widthGauge;
    }

    public final int getHeightGauge() {
        return heightGauge;
    }

    public final void setHeightGauge(int heightGauge) {
        this.heightGauge = heightGauge;
    }

    @Override public final double getWidthMeters() {
        return Math.pow(2.0, widthGauge);
    }

    @Override public final double getHeightMeters() {
        return Math.pow(2.0, heightGauge);
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

    @Override public boolean canConnect(Port otherPort) {
        return otherPort != null &&
               otherPort != this &&
               direction.canConnect(otherPort.getDirection()) &&
               getClass().equals(otherPort.getClass());
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
