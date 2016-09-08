package utils.formula.component

import java.math.BigDecimal

interface ComputationUnit: Cloneable {
    fun computeResult(): BigDecimal
    override fun toString(): String
    public override fun clone(): ComputationUnit
}
