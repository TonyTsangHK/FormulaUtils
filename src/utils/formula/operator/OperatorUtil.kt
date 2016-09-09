package utils.formula.operator

import utils.formula.operator.computation.*
import utils.formula.operator.general.LeftBracket
import utils.formula.operator.general.RightBracket
import utils.formula.operator.postfix.PercentageOperator
import utils.formula.operator.postfix.PermutationOperator
import utils.formula.operator.prefix.RootOperator

object OperatorUtil {
    private val POSTFIX_OPERATORS = arrayOf(PermutationOperator, PercentageOperator)
    private val PREFIX_OPERATORS = arrayOf(RootOperator)
    private val COMPUTATION_OPERATORS = arrayOf(PlusOperator, MinusOperator, MultiplyOperator, DivisionOperator, ModuloOperator, PowerOperator)
    private val GENERAL_OPERATORS = arrayOf(LeftBracket, RightBracket)

    fun createOperator(expression: String?): Operator {
        if (expression == null) {
            throw UnknownOperatorTypeException("Null reference")
        } else {
            POSTFIX_OPERATORS.forEach {
                if (it.isOperatorExpression(expression)) {
                    return it
                }
            }

            PREFIX_OPERATORS.forEach{
                if (it.isOperatorExpression(expression)) {
                    return it
                }
            }

            COMPUTATION_OPERATORS.forEach{
                if (it.isOperatorExpression(expression)) {
                    return it
                }
            }

            GENERAL_OPERATORS.forEach{
                if (it.isOperatorExpression(expression)) {
                    return it
                }
            }

            throw UnknownOperatorTypeException("Unknown operator: " + expression)
        }
    }

    fun isStartsWithOperatorExpression(str: String?, startIndex: Int = 0): Boolean {
        if (str == null) {
            return false
        } else {
            PREFIX_OPERATORS.forEach {
                if (it.isStartWithOperatorExpression(str, startIndex)) {
                    return true
                }
            }

            POSTFIX_OPERATORS.forEach {
                if (it.isStartWithOperatorExpression(str, startIndex)) {
                    return true
                }
            }

            COMPUTATION_OPERATORS.forEach {
                if (it.isStartWithOperatorExpression(str, startIndex)) {
                    return true
                }
            }

            GENERAL_OPERATORS.forEach {
                if (it.isStartWithOperatorExpression(str, startIndex)) {
                    return true
                }
            }

            return false
        }
    }

    fun extractOperator(str: String?, startIndex: Int = 0): Operator? {
        if (str == null) {
            return null
        } else {
            PREFIX_OPERATORS.forEach {
                if (it.isStartWithOperatorExpression(str, startIndex)) {
                    return it
                }
            }

            POSTFIX_OPERATORS.forEach {
                if (it.isStartWithOperatorExpression(str, startIndex)) {
                    return it
                }
            }

            COMPUTATION_OPERATORS.forEach {
                if (it.isStartWithOperatorExpression(str, startIndex)) {
                    return it
                }
            }

            GENERAL_OPERATORS.forEach {
                if (it.isStartWithOperatorExpression(str, startIndex)) {
                    return it
                }
            }

            return  null
        }
    }

    fun extractComputationOperator(str: String?, startIndex: Int): ComputationOperator? {
        if (str == null) {
            return null
        } else {
            COMPUTATION_OPERATORS.forEach {
                if (it.isStartWithOperatorExpression(str, startIndex)) {
                    return it
                }
            }

            return null
        }
    }

    fun extractPrefixOperator(str: String?, startIndex: Int): PrefixOperator? {
        if (str == null) {
            return null
        } else {
            PREFIX_OPERATORS.forEach {
                if (it.isStartWithOperatorExpression(str, startIndex)) {
                    return it
                }
            }

            return null
        }
    }

    fun extractPostfixOperator(str: String?, startIndex: Int): PostfixOperator? {
        if (str == null) {
            return null
        } else {
            POSTFIX_OPERATORS.forEach {
                if (it.isStartWithOperatorExpression(str, startIndex)) {
                    return it
                }
            }

            return null
        }
    }

    fun extractGeneralOperator(str: String?, startIndex: Int): Operator? {
        if (str == null) {
            return null
        } else {
            GENERAL_OPERATORS.forEach {
                if (it.isStartWithOperatorExpression(str, startIndex)) {
                    return it
                }
            }

            return null
        }
    }

    class UnknownOperatorTypeException(msg: String) : RuntimeException(msg)
}
