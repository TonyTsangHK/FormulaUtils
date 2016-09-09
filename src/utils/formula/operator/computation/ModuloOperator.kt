package utils.formula.operator.computation

import utils.formula.component.FormulaComponent
import utils.formula.operator.ComputationOperator
import utils.formula.operator.Operator
import java.math.BigDecimal

/**
 * Created with IntelliJ IDEA.
 * User: Tony Tsang
 * Date: 2016-09-09
 * Time: 12:02
 */
object ModuloOperator: ComputationOperator {
    override val componentType: FormulaComponent.Type
        get() = FormulaComponent.Type.OPERATOR
    override val formulaComponentExpression: String
        get() = EXPRESSION
    override val precedence: Operator.Precedence
        get() = Operator.Precedence.MIDDLE

    // Can't use %, since it is occupied by Percentage operation
    val EXPRESSION = "mod"

    override fun compute(leftOperand: BigDecimal, rightOperand: BigDecimal): BigDecimal {
        return leftOperand.remainder(rightOperand)
    }

    override fun toString(): String {
        return EXPRESSION
    }
}