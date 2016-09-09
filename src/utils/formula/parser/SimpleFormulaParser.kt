package utils.formula.parser

import utils.formula.Formula
import utils.formula.component.ComputationNumber
import utils.formula.operator.ComputationOperator
import utils.formula.operator.OperatorUtil
import utils.formula.operator.PostfixOperator
import utils.formula.operator.PrefixOperator

object SimpleFormulaParser: FormulaParser {
    override fun parseFormula(expression: String): Formula {
        val formula = Formula()
        val formulaExpression = expression.replace("\\s*".toRegex(), "")
        var bufferedChars = StringBuilder()

        var i = 0
        while (i < formulaExpression.length) {
            val generalOperator = OperatorUtil.extractGeneralOperator(formulaExpression, i)
            var prefixOperator: PrefixOperator? = null
            var postfixOperator: PostfixOperator? = null
            var computationOperator: ComputationOperator? = null

            val chr = formulaExpression[i]
            var advance = 0

            if (bufferedChars.length == 0) {
                prefixOperator = OperatorUtil.extractPrefixOperator(formulaExpression, i)
            }

            if (generalOperator == null) {
                postfixOperator = OperatorUtil.extractPostfixOperator(formulaExpression, i)
                if (postfixOperator == null) {
                    computationOperator = OperatorUtil.extractComputationOperator(formulaExpression, i)
                }
            }

            if (
                generalOperator != null || computationOperator != null ||
                postfixOperator != null || prefixOperator != null
            ) {
                if (bufferedChars.length > 0) {
                    formula.addFormulaComponent(ComputationNumber(bufferedChars.toString()))
                    bufferedChars = StringBuilder()
                }

                if (generalOperator != null) {
                    formula.addFormulaComponent(generalOperator)
                    advance = generalOperator.formulaComponentExpression.length
                } else if (computationOperator != null) {
                    formula.addFormulaComponent(computationOperator)
                    advance = computationOperator.formulaComponentExpression.length
                } else if (postfixOperator != null) {
                    formula.addFormulaComponent(postfixOperator)
                    advance = postfixOperator.formulaComponentExpression.length
                } else if (prefixOperator != null) {
                    formula.addFormulaComponent(prefixOperator)
                    advance = prefixOperator.formulaComponentExpression.length
                }
            } else {
                bufferedChars.append(chr)
                advance = 1
            }

            i += advance
        }

        if (bufferedChars.length > 0) {
            formula.addFormulaComponent(ComputationNumber(bufferedChars.toString()))
        }

        return formula
    }
}
