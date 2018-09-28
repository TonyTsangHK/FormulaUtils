package utils.formula

import utils.formula.component.ComputationCompoundUnit
import utils.formula.component.ComputationSingleUnit
import utils.formula.component.ComputationUnit
import utils.formula.exception.FormulaProcessingException
import utils.formula.operator.ComputationOperator
import utils.formula.operator.Operator
import utils.formula.operator.PostfixOperator
import utils.formula.operator.PrefixOperator
import utils.formula.operator.computation.PlusOperator
import java.math.BigDecimal
import java.util.Stack

class ComputationChainer : ComputationUnit {
    private lateinit var computationUnitStack: Stack<ComputationUnit>
    private val prefixOperatorStack: Stack<PrefixOperator>

    init {
        computationUnitStack = Stack<ComputationUnit>()
        prefixOperatorStack = Stack<PrefixOperator>()

        computationUnitStack.push(ComputationCompoundUnit())
    }

    fun addComputationUnit(unit: ComputationUnit) {
        var mutableUnit = unit
        while (!prefixOperatorStack.isEmpty()) {
            mutableUnit = ComputationSingleUnit(mutableUnit, prefixOperatorStack.pop())
        }

        val lastUnit = computationUnitStack.peek()

        if (lastUnit is ComputationCompoundUnit) {
            if (lastUnit.isComplete) {
                throw FormulaProcessingException("utils.formula.Formula syntax error: operator is expected")
            } else {
                if (lastUnit.leftUnit == null) {
                    lastUnit.leftUnit = mutableUnit
                } else if (lastUnit.rightUnit == null) {
                    lastUnit.rightUnit = mutableUnit
                } else {
                    throw FormulaProcessingException("utils.formula.Formula syntax error: missing computation operator")
                }
            }
        } else if (lastUnit is ComputationSingleUnit) {
            if (lastUnit.computationUnit != null) {
                var u = lastUnit.computationUnit
                while (u is ComputationSingleUnit) {
                    if (u.computationUnit != null) {
                        u = u.computationUnit
                    } else {
                        u.computationUnit = mutableUnit
                        break
                    }
                }
                if (u !is ComputationSingleUnit) {
                    throw FormulaProcessingException("utils.formula.Formula syntax error: operator is expected")
                }
            } else {
                lastUnit.computationUnit = mutableUnit
            }
        } else {
            throw FormulaProcessingException("utils.formula.Formula syntax error: operator is expected")
        }
    }

    fun addComputationOperator(operator: ComputationOperator) {
        var lastUnit = computationUnitStack.pop()
        val compoundUnit = convertToCompoundUnit(lastUnit)
        if (compoundUnit.isEmpty) {
            if (operator is PlusOperator) {
                prefixOperatorStack.push(operator as PrefixOperator)
                computationUnitStack.push(lastUnit)
                return
            } else {
                throw FormulaProcessingException("utils.formula.Formula syntax error: Missing operands")
            }
        }
        if (compoundUnit.isComplete) {
            while (true) {
                if (Operator.PRECEDENCE_COMPARATOR.compare(operator.precedence, getComputationUnitOperatorPrecedence(lastUnit)) > 0) {
                    if (lastUnit is ComputationCompoundUnit) {
                        val lastRightUnit = lastUnit.rightUnit
                        val newUnit = ComputationCompoundUnit(
                                lastRightUnit, null, operator)
                        lastUnit.rightUnit = newUnit
                        computationUnitStack.push(lastUnit)
                        computationUnitStack.push(newUnit)
                        break
                    }
                    // ComputatioSingleUnit is not expected
                }
                if (computationUnitStack.isEmpty()) {
                    if (lastUnit is ComputationCompoundUnit) {
                        lastUnit.leftUnit = lastUnit.clone()
                        lastUnit.rightUnit = null
                        lastUnit.computationOperator = operator
                        computationUnitStack.push(lastUnit)
                    }
                    // ComputatioSingleUnit is not expected
                    break
                }
                lastUnit = computationUnitStack.pop()
            }
        } else {
            if (compoundUnit.computationOperator == null) {
                compoundUnit.computationOperator = operator
                computationUnitStack.push(compoundUnit)
            } else {
                throw FormulaProcessingException("utils.formula.Formula syntax error: Multiple computation operators")
            }
        }
    }

    fun addPrefixComputationOperator(operator: PrefixOperator) {
        prefixOperatorStack.push(operator)
    }

    fun addPostfixComputationOperator(operator: PostfixOperator) {
        val lastUnit = computationUnitStack.pop()

        if (lastUnit is ComputationCompoundUnit) {
            if (lastUnit.isEmpty) {
                throw FormulaProcessingException("utils.formula.Formula syntax error: Missing operands")
            } else if (lastUnit.isComplete) {
                if (Operator.PRECEDENCE_COMPARATOR.compare(lastUnit.computationOperator!!.precedence, operator.precedence) > 0) {
                    computationUnitStack.push(ComputationSingleUnit(lastUnit, operator))
                } else {
                    lastUnit.rightUnit = ComputationSingleUnit(lastUnit.rightUnit, operator)
                }
                computationUnitStack.push(lastUnit)
            } else {
                if (lastUnit.leftUnit != null && lastUnit.computationOperator == null &&
                        lastUnit.rightUnit == null) {
                    computationUnitStack.push(ComputationSingleUnit(lastUnit.leftUnit, operator))
                } else {
                    throw FormulaProcessingException(
                            "utils.formula.Formula syntax error: Incomplete computation unit")
                }
            }
        } else if (lastUnit is ComputationSingleUnit) {
            if (lastUnit.isComplete) {
                computationUnitStack.push(ComputationSingleUnit(lastUnit, operator))
            } else if (lastUnit.computationUnit != null) {
                lastUnit.operator = operator
                computationUnitStack.push(lastUnit)
            } else {
                throw FormulaProcessingException("utils.formula.Formula syntax error: Missing operand")
            }
        } else {
            computationUnitStack.push(ComputationSingleUnit(lastUnit, operator))
        }
    }

    private fun convertToCompoundUnit(unit: ComputationUnit): ComputationCompoundUnit {
        if (unit is ComputationCompoundUnit) {
            return unit
        } else if (unit is ComputationSingleUnit) {
            if (unit.operator == null) {
                return ComputationCompoundUnit(unit.computationUnit, null, null)
            } else {
                return ComputationCompoundUnit(unit, null, null)
            }
        } else {
            return ComputationCompoundUnit(unit, null, null)
        }
    }

    fun getComputationUnitOperatorPrecedence(unit: ComputationUnit): Operator.Precedence {
        if (unit is ComputationCompoundUnit) {
            if (unit.computationOperator != null) {
                return unit.computationOperator!!.precedence
            } else {
                throw FormulaProcessingException("utils.formula.Formula syntax error: Operator precedence error")
            }
        } else if (unit is ComputationSingleUnit) {
            if (unit.operator != null) {
                return unit.operator!!.precedence
            } else {
                throw FormulaProcessingException("utils.formula.Formula syntax error: Operator precedence error")
            }
        } else {
            throw FormulaProcessingException("utils.formula.Formula syntax error: Operator precedence error")
        }
    }

    fun complete() {
        val lastUnit = computationUnitStack.peek()
        if (lastUnit is ComputationCompoundUnit) {
            if (lastUnit.leftUnit != null && lastUnit.computationOperator == null &&
                    lastUnit.rightUnit == null) {
                computationUnitStack.pop()
                computationUnitStack.push(lastUnit.leftUnit)
            }
        } else if (lastUnit is ComputationSingleUnit) {
            if (lastUnit.computationUnit != null && lastUnit.operator == null) {
                computationUnitStack.pop()
                computationUnitStack.push(lastUnit.computationUnit)
            }
        }
    }

    @SuppressWarnings("unchecked")
    override fun clone(): ComputationChainer {
        val chainer = ComputationChainer()
        chainer.computationUnitStack = this.computationUnitStack.clone() as Stack<ComputationUnit>
        return chainer
    }

    override fun computeResult(): BigDecimal {
        return computationUnitStack.firstElement().computeResult()
    }

    override fun toString(): String {
        return "(" + computationUnitStack.firstElement().toString() + ")"
    }
}
