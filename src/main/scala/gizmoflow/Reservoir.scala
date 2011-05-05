package gizmoflow

/**
 * Contains fluid(s?) of some composition.
 */
class Reservoir extends Node {
  def pressure: Double

  def composition: Mixture

}