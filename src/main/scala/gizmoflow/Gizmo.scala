package gizmoflow
import scalaquantity.Units._

/**
 * 
 */
trait Gizmo {

  private var _ports: Map[Symbol, Port] = Map()

  def ports = _ports

  def addPort(name: Symbol, portType: StuffType) {
    _ports += (name -> new Port(name, this, portType))
  }

  def simulate(time: Time)
}
