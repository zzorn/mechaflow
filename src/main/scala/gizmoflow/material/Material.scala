package gizmoflow.material

import scalaquantity.Units._

/**
 * Some basic material.
 */
case class Material(name: Symbol,
                    density: Density,
                    heatCapacity: HeatCapacity,
                    viscosity: DynamicViscosity,
                    state: MaterialState) {
  
}