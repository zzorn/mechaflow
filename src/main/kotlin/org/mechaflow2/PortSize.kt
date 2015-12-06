package org.mechaflow2

/**
 * Represents the size of a port.  The sizes are available in integer increments.
 * @param gauge size of the port, expressed as 2^gauge meters.
 *              Ports of different sizes can be connected.
*/
data class PortSize(val gauge: Int = PortSize.MEDIUM_GAUGE) {

    /**
     * Approximate width and height of the port, in meters.
     */
    val sizeMeters : Double = Math.pow(2.0, gauge.toDouble())

    /**
     * Diameter of the internal part of the port (e.g. inner pipe diameter)
     */
    val internalDiameter : Double = sizeMeters * INTERNAL_DIAMETER_FRACTION

    /**
     * Area of the internal part of the port (e.g. inner pipe area)
     */
    val internalArea : Double = Math.PI * Math.pow(0.5 * internalDiameter, 2.0)


    companion object {
        public const val SMALL_GAUGE = -4
        public const val MEDIUM_GAUGE = -2
        public const val LARGE_GAUGE = 0

        public val SMALL = PortSize(SMALL_GAUGE)
        public val MEDIUM = PortSize(MEDIUM_GAUGE)
        public val LARGE = PortSize(LARGE_GAUGE)

        private const val INTERNAL_DIAMETER_FRACTION = 0.8
    }

}