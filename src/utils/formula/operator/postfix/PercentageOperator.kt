package utils.formula.operator.postfix

import java.math.BigDecimal

import utils.formula.component.FormulaComponent
import utils.formula.operator.Operator
import utils.formula.operator.PostfixOperator

object PercentageOperator: PostfixOperator {
    override val componentType: FormulaComponent.Type
        get() = FormulaComponent.Type.OPERATOR
    override val formulaComponentExpression: String
        get() = EXPRESSION

    val EXPRESSION = "%"

    override fun isOperatorExpression(expression: String): Boolean {
        return expression == EXPRESSION
    }

    override fun isStartWithOperatorExpression(expression: String, startIndex: Int): Boolean {
        return expression.startsWith(EXPRESSION, startIndex)
    }

    override val precedence: Operator.Precedence
        get() = Operator.Precedence.COMMON

    override fun compute(operand: BigDecimal): BigDecimal {
        return operand.divide(BigDecimal(100), Operator.DEFAULT_MATH_CONTEXT)
    }

    override fun toString(): String {
        return EXPRESSION
    }
}
