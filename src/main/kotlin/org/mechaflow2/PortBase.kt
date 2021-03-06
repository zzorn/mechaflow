package org.mechaflow2


/**
 * Creates a new port.
 * @param name user readable name/id of the port.
 * @param description user readable english description of the port, or null if no description is provided.
 * @param direction the direction of energy, matter or information transmission in this port.
 * @param widthGauge width class of the port.  Size fits inside 2 ^ widthGauge meters.
 * @param heightGauge height class of the port.  Size fits inside 2 ^ heightGauge meters.
 */
abstract class PortBase(override val name: String,
                        override val direction: PortDirection = PortDirection.INOUT,
                        override val description: String? = null,
                        override var size: PortSize = PortSize.MEDIUM) : Port {

    override var connectedPort: Port? = null
    override lateinit var machine: Machine

    override fun init(machine: Machine) {
        this.machine = machine
    }

    override fun connect(otherPort: Port) {
        // If already connected, no need to do anything
        if (connectedPort === otherPort) return

        // Check that we can connect
        if (!canConnect(otherPort)) throw IllegalArgumentException("Can not connect the port " + this + " to the port " + otherPort + ".")

        // Disconnect existing connection
        disconnect()

        // Update connected port
        connectedPort = otherPort

        // Let other port connect as well, if not already connected
        if (otherPort.connectedPort !== this) {
            otherPort.connect(this)
        }

        onConnected(otherPort)
    }

    override fun disconnect() {
        if (connectedPort != null) {
            val previousConnectedPort = connectedPort
            connectedPort = null

            if (previousConnectedPort != null && previousConnectedPort.isConnected()) {
                previousConnectedPort.disconnect()
                onDisconnected(previousConnectedPort)
            }
        }
    }

    override fun canConnect(otherPort: Port?): Boolean {
        return otherPort != null &&
               otherPort !== this &&
               direction.canConnect(otherPort.direction) &&
               javaClass == otherPort.javaClass
    }

    /**
     * Called when connecting to another port.
     */
    protected open fun onConnected(otherPort: Port) {}

    /**
     * Called when disconnecting from a port.
     */
    protected open fun onDisconnected(previousConnectedPort: Port) {}

}
