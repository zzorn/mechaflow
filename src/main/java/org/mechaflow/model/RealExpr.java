package org.mechaflow.model;

/**
 * Real constant expression.
 */
public final class RealExpr implements Expr {
    private final double value;

    public RealExpr(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override public Type getType() {
        return Real.getReal();
    }
}
