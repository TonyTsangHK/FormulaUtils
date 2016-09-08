package utils.formula.operator.computation

import java.math.BigDecimal

import utils.formula.component.FormulaComponent
import utils.formula.operator.ComputationOperator
import utils.formula.operator.Operator

object DivisionOperator: ComputationOperator {
    override val componentType: FormulaComponent.Type
        get() = FormulaComponent.Type.OPERATOR
    override val formulaComponentExpression: String
        get() = EXPRESSION_NORMAL

    val EXPRESSION_NORMAL = "÷"
    val EXPRESSION_COMP = "/"

    override fun compute(leftOperand: BigDecimal, rightOperand: BigDecimal): BigDecimal {
        return leftOperand.divide(rightOperand, Operator.DEFAULT_MATH_CONTEXT)
    }

    override fun isOperatorExpression(expression: String): Boolean {
        return expression == EXPRESSION_NORMAL || expression == EXPRESSION_COMP
    }

    override fun toString(): String {
        return EXPRESSION_NORMAL
    }

    override val precedence: Operator.Precedence
        get() = Operator.Precedence.MIDDLE
}
