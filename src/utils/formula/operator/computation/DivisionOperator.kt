package utils.formula.operator.computation

import utils.formula.component.FormulaComponent
import utils.formula.operator.ComputationOperator
import utils.formula.operator.Operator
import java.math.BigDecimal

object DivisionOperator: ComputationOperator {
    override val componentType: FormulaComponent.Type
        get() = FormulaComponent.Type.OPERATOR
    override val formulaComponentExpression: String
        get() = EXPRESSION_NORMAL
    override val precedence: Operator.Precedence
        get() = Operator.Precedence.MIDDLE

    val EXPRESSION_NORMAL = "÷"
    val EXPRESSION_COMP = "/"

    override fun compute(leftOperand: BigDecimal, rightOperand: BigDecimal): BigDecimal {
        return leftOperand.divide(rightOperand, Operator.DEFAULT_MATH_CONTEXT)
    }

    override fun isOperatorExpression(expression: String): Boolean {
        return expression == EXPRESSION_NORMAL || expression == EXPRESSION_COMP
    }

    override fun isStartWithOperatorExpression(expression: String, startIndex: Int): Boolean {
        return expression.startsWith(EXPRESSION_NORMAL, startIndex) || expression.startsWith(EXPRESSION_COMP, startIndex)
    }

    override fun toString(): String {
        return EXPRESSION_NORMAL
    }
}
