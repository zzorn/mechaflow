package gizmoflow.material.phase

import scalaquantity.Units._

/**
 * The material properties of a phase of some material (e.g. water, ice, water vapor, or carbon, diamond, etc)
 */
case class Phase(name: Symbol,
                 state: MaterialState,
                 density: Density,
                 specificHeatCapacity: SpecificHeatCapacity,
                 volumetricThermalExpansion: One/Temperature,
                 viscosity: DynamicViscosity   ) {
}