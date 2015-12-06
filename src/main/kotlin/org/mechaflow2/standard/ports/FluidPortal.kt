package org.mechaflow2.standard.ports

/**
 * A connection to a specific point in some fluid volume.
 * Has pressure, temperature, density, and fluid mixture at the point,
 * and allows siphoning or adding fluid at that point.
 */
interface FluidPortal {

    val pressure_Pa: Double
    val temperature_K: Double
    val density: Double
    val viscosity: Double

    /**
     * Moves fluids between this and the specified other portal
     * @param volume_m3 amount of fluid volume to move, if positive, moves to the other portal, if negative, moves from the other portal.
     * @param otherPortal other portal to move fluid from/to
     */
    fun moveFluid(volume_m3: Double, otherPortal: FluidPortal)

}