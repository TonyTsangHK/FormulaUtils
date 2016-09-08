package utils.formula.operator

import java.math.BigDecimal

interface SingleComputationOperator : Operator {
    fun compute(operand: BigDecimal): BigDecimal
}
