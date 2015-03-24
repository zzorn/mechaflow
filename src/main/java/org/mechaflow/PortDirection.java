package org.mechaflow;

/**
 * Represents the possible directions of energy, matter or information transmission in port.
 */
public enum PortDirection {
    IN(true, false),
    OUT(false, true),
    INOUT(true, true);

    private final boolean isInput;
    private final boolean isOutput;

    PortDirection(boolean isInput, boolean isOutput) {
        this.isInput = isInput;
        this.isOutput = isOutput;
    }

    public boolean isInput() {
        return isInput;
    }

    public boolean isOutput() {
        return isOutput;
    }

    public boolean canConnect(PortDirection otherDirection) {
        switch (this) {
            case IN:
                return otherDirection == OUT;
            case OUT:
                return otherDirection == IN;
            case INOUT:
                return otherDirection == INOUT;
            default:
                throw new IllegalStateException("Unknown PortDirection: " + this);
        }
    }
}
