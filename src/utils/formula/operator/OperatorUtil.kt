package utils.formula.operator

import utils.formula.operator.computation.DivisionOperator
import utils.formula.operator.computation.MinusOperator
import utils.formula.operator.computation.MultiplyOperator
import utils.formula.operator.computation.PlusOperator
import utils.formula.operator.computation.PowerOperator
import utils.formula.operator.general.LeftBracket
import utils.formula.operator.general.RightBracket
import utils.formula.operator.postfix.PercentageOperator
import utils.formula.operator.postfix.PermutationOperator
import utils.formula.operator.prefix.RootOperator

object OperatorUtil {
    fun createOperator(expression: String?): Operator {
        if (expression == null) {
            throw UnknownOperatorTypeException("Null reference")
        } else if (expression == PlusOperator.EXPRESSION) {
            return PlusOperator
        } else if (expression == MinusOperator.EXPRESSION) {
            return MinusOperator
        } else if (expression == MultiplyOperator.EXPRESSION_NORMAL || expression == MultiplyOperator.EXPRESSION_COMP) {
            return MultiplyOperator
        } else if (expression == DivisionOperator.EXPRESSION_NORMAL || expression == DivisionOperator.EXPRESSION_COMP) {
            return DivisionOperator
        } else if (expression == PowerOperator.EXPRESSION) {
            return PowerOperator
        } else if (expression == LeftBracket.EXPRESSION) {
            return LeftBracket
        } else if (expression == RightBracket.EXPRESSION) {
            return RightBracket
        } else {
            throw UnknownOperatorTypeException("Unknown operator: " + expression)
        }
    }

    fun isStartsWithOperatorExpression(str: String?): Boolean {
        if (str == null) {
            return false
        } else {
            return str.startsWith(PlusOperator.EXPRESSION) ||
                    str.startsWith(MinusOperator.EXPRESSION) ||
                    str.startsWith(MultiplyOperator.EXPRESSION_NORMAL) ||
                    str.startsWith(MultiplyOperator.EXPRESSION_COMP) ||
                    str.startsWith(DivisionOperator.EXPRESSION_NORMAL) ||
                    str.startsWith(DivisionOperator.EXPRESSION_COMP) ||
                    str.startsWith(PowerOperator.EXPRESSION) ||
                    str.startsWith(LeftBracket.EXPRESSION) ||
                    str.startsWith(RightBracket.EXPRESSION)
        }
    }

    fun isStartsWithOperatorExpression(str: String?, startIndex: Int): Boolean {
        if (str == null) {
            return false
        } else {
            return str.startsWith(PlusOperator.EXPRESSION, startIndex) ||
                    str.startsWith(MinusOperator.EXPRESSION, startIndex) ||
                    str.startsWith(MultiplyOperator.EXPRESSION_NORMAL, startIndex) ||
                    str.startsWith(MultiplyOperator.EXPRESSION_COMP, startIndex) ||
                    str.startsWith(DivisionOperator.EXPRESSION_NORMAL, startIndex) ||
                    str.startsWith(DivisionOperator.EXPRESSION_COMP, startIndex) ||
                    str.startsWith(PowerOperator.EXPRESSION, startIndex) ||
                    str.startsWith(LeftBracket.EXPRESSION, startIndex) ||
                    str.startsWith(RightBracket.EXPRESSION, startIndex)
        }
    }

    fun extractOperator(str: String?): Operator? {
        if (str == null) {
            return null
        } else {
            if (str.startsWith(PlusOperator.EXPRESSION)) {
                return PlusOperator
            } else if (str.startsWith(MinusOperator.EXPRESSION)) {
                return MinusOperator
            } else if (str.startsWith(MultiplyOperator.EXPRESSION_NORMAL) || str.startsWith(MultiplyOperator.EXPRESSION_COMP)) {
                return MultiplyOperator
            } else if (str.startsWith(DivisionOperator.EXPRESSION_COMP) || str.startsWith(DivisionOperator.EXPRESSION_NORMAL)) {
                return DivisionOperator
            } else if (str.startsWith(PowerOperator.EXPRESSION)) {
                return PowerOperator
            } else if (str.startsWith(LeftBracket.EXPRESSION)) {
                return LeftBracket
            } else if (str.startsWith(RightBracket.EXPRESSION)) {
                return RightBracket
            } else {
                return null
            }
        }
    }

    fun extractComputationOperator(str: String?, startIndex: Int): ComputationOperator? {
        if (str == null) {
            return null
        } else {
            if (str.startsWith(PlusOperator.EXPRESSION, startIndex)) {
                return PlusOperator
            } else if (str.startsWith(MinusOperator.EXPRESSION, startIndex)) {
                return MinusOperator
            } else if (str.startsWith(MultiplyOperator.EXPRESSION_NORMAL, startIndex) || str.startsWith(MultiplyOperator.EXPRESSION_COMP, startIndex)) {
                return MultiplyOperator
            } else if (str.startsWith(DivisionOperator.EXPRESSION_COMP, startIndex) || str.startsWith(DivisionOperator.EXPRESSION_NORMAL, startIndex)) {
                return DivisionOperator
            } else if (str.startsWith(PowerOperator.EXPRESSION, startIndex)) {
                return PowerOperator
            } else {
                return null
            }
        }
    }

    fun extractPrefixOperator(str: String?, startIndex: Int): PrefixOperator? {
        if (str == null) {
            return null
        } else {
            if (str.startsWith(RootOperator.EXPRESSION, startIndex)) {
                return RootOperator
            } else {
                return null
            }
        }
    }

    fun extractPostfixOperator(str: String?, startIndex: Int): PostfixOperator? {
        if (str == null) {
            return null
        } else {
            if (str.startsWith(PermutationOperator.EXPRESSION, startIndex)) {
                return PermutationOperator
            } else if (str.startsWith(PercentageOperator.EXPRESSION, startIndex)) {
                return PercentageOperator
            } else {
                return null
            }
        }
    }

    fun extractGeneralOperator(str: String?, startIndex: Int): Operator? {
        if (str == null) {
            return null
        } else {
            if (str.startsWith(LeftBracket.EXPRESSION, startIndex)) {
                return LeftBracket
            } else if (str.startsWith(RightBracket.EXPRESSION, startIndex)) {
                return RightBracket
            } else {
                return null
            }
        }
    }

    class UnknownOperatorTypeException(msg: String) : RuntimeException(msg)
}
