package org.mechaflow2.standard

import org.mechaflow2.standard.material.PhaseDiagram
import java.awt.Color

/**
 *
 */
data class Material(val name: String,
                    val densitySolid: Double,
                    val densityLiquid: Double,
                    val friction: Double,
                    val ignitionTemperature: Double,
                    val thermalExpansion: Double,
                    val thermalConductivity: Double,
                    val heatCapacity: Double,
                    val phaseDiagram: PhaseDiagram,
                    val color: Color = Color.GRAY) {

}