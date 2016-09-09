package utils.formula.operator.prefix

import utils.formula.component.FormulaComponent
import utils.formula.operator.Operator
import utils.formula.operator.PrefixOperator
import java.math.BigDecimal

/**
 * Created with IntelliJ IDEA.
 * User: Tony Tsang
 * Date: 2016-09-09
 * Time: 15:56
 */
object NegateOperator: PrefixOperator {
    override val componentType: FormulaComponent.Type
        get() = FormulaComponent.Type.OPERATOR
    override val formulaComponentExpression: String
        get() = EXPRESSION
    override val precedence: Operator.Precedence
        get() = Operator.Precedence.COMMON

    val EXPRESSION = "-"

    override fun compute(operand: BigDecimal): BigDecimal {
        return operand.negate()
    }
}