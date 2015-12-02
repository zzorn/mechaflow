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
    val sizeMeters : Double
        get() = Math.pow(2.0, gauge.toDouble())


    companion object {
        public const val SMALL_GAUGE = -4
        public const val MEDIUM_GAUGE = -2
        public const val LARGE_GAUGE = 0

        public val SMALL = PortSize(SMALL_GAUGE)
        public val MEDIUM = PortSize(MEDIUM_GAUGE)
        public val LARGE = PortSize(LARGE_GAUGE)
    }

}