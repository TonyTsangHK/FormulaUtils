package utils.formula.operator.general

import utils.formula.component.FormulaComponent
import utils.formula.operator.Operator

object RightBracket: Operator {
    override val componentType: FormulaComponent.Type
        get() = FormulaComponent.Type.OPERATOR
    override val formulaComponentExpression: String
        get() = EXPRESSION
    override val precedence: Operator.Precedence
        get() = Operator.Precedence.GENERAL

    val EXPRESSION = ")"

    override fun toString(): String {
        return EXPRESSION
    }
}
