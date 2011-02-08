package gizmoflow.material

import scalaquantity.Units._

/**
 * Empty matter
 */
object NoMatter extends Matter(0, new Mixture(Map()), 0*Kelvin)
