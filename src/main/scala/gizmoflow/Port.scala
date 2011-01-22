package gizmoflow

/**
 * A port on a node that can be connected to another port of the same type.
 */
// TODO: What about distinct input/output ports?  E.g. item ports and information ports could be that..  They could be two way too though.

// TODO: Which party takes care of updating transfers?  both?
case class Port(name: Symbol, node: Gizmo, portType: StuffType) {

  private var _connectedPort: Port = null

  def connectedPort: Port = _connectedPort
  def getConnectedPort: Option[Port] = if (_connectedPort == null) None else Some(_connectedPort)

  /**
   * Connects to the specified port.
   */
  def connectTo(port: Port) {
    if (_connectedPort != port) {
      if (port == this) throw new IllegalArgumentException("Can not connect port "+this+" to itself")
      if (port.portType != portType) throw new IllegalArgumentException("Can not connect port "+this+" to port "+port+": Incompatible port types")

      disconnect()

      _connectedPort = port
      _connectedPort.connectTo(this)

      // TODO: Start transfer
    }
  }

  /**
   * Disconnects from currently connected port.
   */
  def disconnect() {
    if (_connectedPort != null) {
      val otherPort = _connectedPort 
      _connectedPort = null
      otherPort.disconnect();

      // TODO: Interrupt any ongoing transfer
    }
  }

}
