package utils.formula.operator.computation

import utils.formula.component.FormulaComponent
import utils.formula.operator.ComputationOperator
import utils.formula.operator.Operator
import utils.formula.operator.PrefixOperator
import java.math.BigDecimal

object PlusOperator: ComputationOperator, PrefixOperator {
    override val componentType: FormulaComponent.Type
        get() = FormulaComponent.Type.OPERATOR
    override val formulaComponentExpression: String
        get() = EXPRESSION
    override val precedence: Operator.Precedence
        get() = Operator.Precedence.LOWEST

    val EXPRESSION = "+"

    override fun compute(operand: BigDecimal): BigDecimal {
        return operand
    }

    override fun compute(leftOperand: BigDecimal, rightOperand: BigDecimal): BigDecimal {
        return leftOperand + rightOperand
    }

    override fun toString(): String {
        return EXPRESSION
    }
}
