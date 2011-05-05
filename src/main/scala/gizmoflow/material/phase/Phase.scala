package gizmoflow.material.phase

import scalaquantity.Units._
import org.scalaprops.Bean
import gizmoflow.material.phase.MaterialState
import org.scalaprops.ui.editors.SelectionEditorFactory

/**
 * The material properties of a phase of some material (e.g. water, ice, water vapor, or carbon, diamond, etc)
 */
class Phase extends Bean {

  val name = p('name, "")
  val state = p[MaterialState]('state, Liquid).editor(new SelectionEditorFactory[MaterialState](MaterialState.states))
  val density = p('density, new Density(1))
  val specificHeatCapacity = p('specificHeatCapacity, new SpecificHeatCapacity(1))
  val volumetricThermalExpansion = p[One/Temperature]('volumetricThermalExpansion, new (One/Temperature)(1))
  val dynamicViscosity = p('dynamicViscosity, new DynamicViscosity(1))
  val compressibility = p('compressibility, new (One/Pressure)(1))
  val molarMass = p('molarMass, new (kg/mol)(1))

}