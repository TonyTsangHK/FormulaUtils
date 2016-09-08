package utils.formula.operator.prefix

import java.math.BigDecimal

import utils.formula.component.FormulaComponent
import utils.formula.exception.NegativeRootException
import utils.formula.operator.Operator
import utils.formula.operator.PrefixOperator
import utils.formula.operator.computation.PowerOperator

object RootOperator: PrefixOperator {
    override val componentType: FormulaComponent.Type
        get() = FormulaComponent.Type.OPERATOR
    override val formulaComponentExpression: String
        get() = EXPRESSION
    override val precedence: Operator.Precedence
        get() = Operator.Precedence.COMMON

    val EXPRESSION = "√"

    override fun compute(operand: BigDecimal): BigDecimal {
        if (operand.signum() == -1) {
            throw NegativeRootException("Negative number has no root: " + operand)
        } else if (operand.signum() == 0) {
            return BigDecimal.ZERO
        } else {
            return PowerOperator.compute(operand, BigDecimal(0.5))
        }
    }

    override fun isOperatorExpression(expression: String): Boolean {
        return EXPRESSION == expression
    }

    override fun toString(): String {
        return EXPRESSION
    }
}
