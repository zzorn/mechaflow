package org.mechaflow2

/**
 * Represents the possible directions of energy, matter or information transmission in port.
 */
enum class PortDirection private constructor(val isInput: Boolean, val isOutput: Boolean) {

    IN(true, false),
    OUT(false, true),
    INOUT(true, true);

    fun canConnect(otherDirection: PortDirection): Boolean {
        when (this) {
            IN -> return otherDirection == OUT
            OUT -> return otherDirection == IN
            INOUT -> return otherDirection == INOUT
            else -> throw IllegalStateException("Unknown PortDirection: " + this)
        }
    }
}
