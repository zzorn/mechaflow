package org.mechaflow2.standard.ports

/**
 * Holds the acceptable range for a signal.
 */
data class SignalRange(val min: Double  = Double.NEGATIVE_INFINITY,
                       val max: Double  = Double.POSITIVE_INFINITY) {

    init {
        if (max < min) throw IllegalArgumentException("The specified maximum value ($max) was smaller than the minimum value ($min)")
    }

    companion object {
        val ZERO_TO_ONE = SignalRange(0.0, 1.0)
        val MINUS_ONE_TO_ONE = SignalRange(-1.0, 1.0)
        val POSITIVE_OR_ZERO = SignalRange(0.0, Double.POSITIVE_INFINITY)
        val UNLIMITED = SignalRange(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY)
    }

    /**
     * Clamps the given value to this range.
     */
    fun clamp(value: Double): Double = if (value < min) min else if (value > max) max else value

}