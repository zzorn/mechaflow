package org.mechaflow2.standard.ports

import org.flowutils.time.Time
import org.mechaflow2.Port
import org.mechaflow2.PortBase
import org.mechaflow2.PortDirection
import org.mechaflow2.PortSize

/**
 * A port for transporting fluids (liquids, gases, and maybe grainy loose matter as well) between machines.
 */
class FluidPort(name: String,
                val fluidPortal: FluidPortal,
                description: String? = null,
                size: PortSize = PortSize.MEDIUM,
                val roughness_mm: Double = 1.0) : PortBase(name, PortDirection.INOUT, description, size) {

    // TODO: Information on maximum supported pressure and flow, and handling of failure conditions

    private var currentFlowSpeed_m_per_s = 0.0



    override fun onDisconnected(previousConnectedPort: Port) {
        currentFlowSpeed_m_per_s = 0.0
    }

    override fun propagate(time: Time) {
        val otherPort: FluidPort? = connectedPort as FluidPort?
        if (otherPort != null && isSlave(otherPort)) {
            // If we are the master port, we handle the propagation

            val otherPortal = otherPort.fluidPortal

            val area_m2 = size.internalArea
            val time_s = time.lastStepDurationSeconds
            val p1 = fluidPortal.pressure_Pa
            val p2 = otherPortal.pressure_Pa
            var deltaP = p1 - p2
            if (deltaP != 0.0) {

                val outFlow = deltaP > 0
                val density = if (outFlow) fluidPortal.density else otherPortal.density
                val viscosity = if (outFlow) fluidPortal.viscosity else otherPortal.viscosity
                val portInternalDiameter = size.internalDiameter
                val portLength = portInternalDiameter * PORT_LENGTH_RELATED_TO_DIAMETER

                // Calculate pressure loss due to viscosity & friction
                val pressureLoss = calculatePressureLoss(portLength, portInternalDiameter, roughness_mm, density, viscosity, currentFlowSpeed_m_per_s)

                // Apply pressure loss to pressure delta
                if (deltaP > 0) deltaP = Math.max(0.0, deltaP - pressureLoss)
                else deltaP = Math.min(0.0, deltaP + pressureLoss)

                // Update velocity based on pressure difference and fluid in the port
                val velocityChange = time_s * deltaP / (portLength * density)

                // Update flow speed
                currentFlowSpeed_m_per_s += velocityChange

                // Move fluid between portals
                val movedVolume = area_m2 * currentFlowSpeed_m_per_s * time_s
                fluidPortal.moveFluid(movedVolume, otherPortal)
            }
        }
    }

    /**
     * @return pressure loss in pascals due to friction against the pipe and viscosity
     */
    private fun calculatePressureLoss(pipeLength: Double,
                                      pipeInternalDiameter: Double,
                                      pipeRoughness_mm: Double,
                                      fluidDensity: Double,
                                      fluidViscosity: Double,
                                      flowSpeed_m_per_s: Double): Double {

        fun calculateDarcyFrictionFactor(): Double {
            // Use Swamee-Jain formula to approximate the Darcy friction factor
            val relativeRoughness = (pipeRoughness_mm / 1000.0) / pipeInternalDiameter
            val reynoldsNumber = Math.abs(flowSpeed_m_per_s) * pipeInternalDiameter / fluidViscosity
            val divisor = Math.log10(relativeRoughness / 3.7 + 5.74 / Math.pow(reynoldsNumber, 0.9))
            return 0.25 / (divisor * divisor)
        }

        // Use Darcy–Weisbach for pressure loss due to viscosity & friction
        return calculateDarcyFrictionFactor() *
                (pipeLength / pipeInternalDiameter) *
                0.5 * fluidDensity *
                flowSpeed_m_per_s * flowSpeed_m_per_s
    }


    private fun isSlave(otherPort: FluidPort) = this.hashCode() > otherPort.hashCode()

    companion object {
        const val PORT_LENGTH_RELATED_TO_DIAMETER = 0.2
    }
}