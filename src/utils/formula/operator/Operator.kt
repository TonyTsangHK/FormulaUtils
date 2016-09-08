package utils.formula.operator

import java.math.MathContext
import java.math.RoundingMode
import java.util.Comparator

import utils.formula.component.FormulaComponent

/**
 * utils.formula.Formula operator interface
 */
interface Operator: FormulaComponent {
    companion object {
        /**
         * Default precision for big decimal division
         */
        val DEFAULT_RESULT_PRECISION = 16

        /**
         * Default math context for big decimal division
         */
        val DEFAULT_MATH_CONTEXT = MathContext(DEFAULT_RESULT_PRECISION, RoundingMode.HALF_UP)

        val PRECEDENCE_COMPARATOR = PrecedenceComparator()
    }

    /**
     * Check whether the provided expression represent an operator or not

     * @param expression target expression string
     * *
     * *
     * @return check result
     */
    fun isOperatorExpression(expression: String): Boolean

    /**
     * Get operator precedency

     * @return precedency
     */
    val precedence: Precedence

    enum class Precedence constructor(val precedence: Int, val desc: String) {
        LOWEST(1, "Lowest precedence"), MIDDLE(2, "Middle precedence"), HIGHEST(3, "Highest precedence"),
        COMMON(4, "Common precedence"), GENERAL(5, "General precedence");

        override fun toString(): String {
            return this.desc
        }
    }

    class PrecedenceComparator : Comparator<Precedence> {
        override fun compare(o1: Precedence, o2: Precedence): Int {
            return o1.precedence - o2.precedence
        }
    }
}
