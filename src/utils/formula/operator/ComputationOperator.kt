package utils.formula.operator

import java.math.BigDecimal

interface ComputationOperator : Operator {
    fun compute(leftOperand: BigDecimal, rightOperand: BigDecimal): BigDecimal
}