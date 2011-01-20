package gizmoflow.material

/**
 * A mix of different types of materials in some concentrations.
 * The concentrations map defines relative amounts of the specified materials.
 */
case class Mixture(concentrations: Map[Double, Material]) extends Material {
  
}