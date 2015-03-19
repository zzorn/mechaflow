package org.mechaflow.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Expression for multiplying a number of factors.
 */
public final class MulExpr implements Expr {
    private List<Expr> factors;

    public MulExpr(Expr a, Expr b) {
        factors = new ArrayList<Expr>(2);
        factors.add(a);
        factors.add(b);
    }

    public MulExpr(List<Expr> factors) {
        this.factors = factors;
    }

    @Override public Type getType() {
        // NOTE: Need to do type checking to ensure all are compatible types.
        return factors.get(0).getType();
    }
}