package org.mechaflow.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Expression for adding a number of terms.
 */
public class AddExpr implements Expr {
    private List<Expr> terms;

    public AddExpr(Expr a, Expr b) {
        terms = new ArrayList<Expr>(2);
        terms.add(a);
        terms.add(b);
    }

    public AddExpr(List<Expr> terms) {
        this.terms = terms;
    }

    @Override public Type getType() {
        // NOTE: Need to do type checking to ensure all are compatible types.
        return terms.get(0).getType();
    }
}
