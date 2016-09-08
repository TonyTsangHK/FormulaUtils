package utils.formula.operator.postfix

import java.math.BigDecimal

import utils.formula.component.FormulaComponent
import utils.formula.exception.NonPermutableException
import utils.formula.operator.Operator
import utils.formula.operator.PostfixOperator

object PermutationOperator: PostfixOperator {
    override val componentType: FormulaComponent.Type
        get() = FormulaComponent.Type.OPERATOR
    override val formulaComponentExpression: String
        get() = EXPRESSION
    override val precedence: Operator.Precedence
        get() = Operator.Precedence.COMMON

    val EXPRESSION = "!"

    override fun isOperatorExpression(expression: String): Boolean {
        return expression == EXPRESSION
    }

    override fun compute(operand: BigDecimal): BigDecimal {
        val remain = (operand % BigDecimal("1")).toDouble()
        if (remain > 0 || operand.compareTo(BigDecimal(0)) < 0) {
            throw NonPermutableException("Non permutable operand: " + operand.toString())
        } else {
            val bigTwo = BigDecimal(2)
            val bigOne = BigDecimal(1)
            val bigZero = BigDecimal(0)
            if (operand.compareTo(bigTwo) == 0) {
                return bigTwo
            } else if (operand.compareTo(bigZero) == 0) {
                return bigZero
            } else if (operand.compareTo(bigOne) == 0) {
                return bigOne
            } else {
                var result = operand
                var current = operand
                while (current > bigTwo) {
                    current -= bigOne

                    result = result.multiply(
                        current, Operator.DEFAULT_MATH_CONTEXT
                    )
                }
                return result
            }
        }
    }

    override fun toString(): String {
        return EXPRESSION
    }
}
