package utils.formula.operator.computation

import java.math.BigDecimal

import utils.formula.component.FormulaComponent
import utils.formula.operator.ComputationOperator
import utils.formula.operator.Operator

object PowerOperator: ComputationOperator {
    override val componentType: FormulaComponent.Type
        get() = FormulaComponent.Type.OPERATOR
    override val formulaComponentExpression: String
        get() = EXPRESSION

    val EXPRESSION = "^"

    override fun compute(leftOperand: BigDecimal, rightOperand: BigDecimal): BigDecimal {
        val signum = rightOperand.signum()
        if (signum == 0) {
            return BigDecimal(1)
        } else if (signum > 0) {
            val parts = rightOperand.divideAndRemainder(BigDecimal.ONE)
            val remaining = parts[1]
            if (remaining > BigDecimal.ZERO) {
                val factor = rightOperand.toInt()
                return leftOperand.pow(factor).multiply(
                    BigDecimal(Math.pow(leftOperand.toDouble(), remaining.toDouble())),
                    Operator.DEFAULT_MATH_CONTEXT
                )
            } else {
                return leftOperand.pow(rightOperand.intValueExact())
            }
        } else {
            return BigDecimal(1).divide(
                compute(leftOperand, rightOperand.abs()),
                Operator.DEFAULT_MATH_CONTEXT
            )
        }
    }

    override fun isOperatorExpression(expression: String): Boolean {
        return expression == EXPRESSION
    }

    override fun toString(): String {
        return EXPRESSION
    }

    override val precedence: Operator.Precedence
        get() = Operator.Precedence.HIGHEST
}
