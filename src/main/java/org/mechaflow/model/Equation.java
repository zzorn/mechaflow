package org.mechaflow.model;

/**
 *
 */
public class Equation {

    private final Expr leftHandSide;
    private final Expr rightHandSide;

    public Equation(Expr leftHandSide, Expr rightHandSide) {
        this.leftHandSide = leftHandSide;
        this.rightHandSide = rightHandSide;
    }
}
