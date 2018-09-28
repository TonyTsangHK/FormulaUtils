package utils.formula.operator.postfix

import utils.formula.component.FormulaComponent
import utils.formula.operator.Operator
import utils.formula.operator.PostfixOperator
import java.math.BigDecimal

object PercentageOperator: PostfixOperator {
    override val componentType: FormulaComponent.Type
        get() = FormulaComponent.Type.OPERATOR
    override val formulaComponentExpression: String
        get() = EXPRESSION
    override val precedence: Operator.Precedence
        get() = Operator.Precedence.COMMON

    val EXPRESSION = "%"

    override fun compute(operand: BigDecimal): BigDecimal {
        return operand.divide(BigDecimal(100), Operator.DEFAULT_MATH_CONTEXT)
    }

    override fun toString(): String {
        return EXPRESSION
    }
}
