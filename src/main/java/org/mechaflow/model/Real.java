package org.mechaflow.model;

/**
 * Real type.
 */
public final class Real implements Type {

    private static final Real REAL = new Real();

    /**
     * @return instance.
     */
    public static Real getReal() {
        return REAL;
    }

    private Real() {
    }
}
