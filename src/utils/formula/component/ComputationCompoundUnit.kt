package utils.formula.component

import java.math.BigDecimal

import utils.formula.exception.IncompleteComputationCompoundUnitException
import utils.formula.operator.ComputationOperator

class ComputationCompoundUnit: ComputationUnit {
    var leftUnit: ComputationUnit?
    var rightUnit: ComputationUnit?
    var computationOperator: ComputationOperator?

    @JvmOverloads
    constructor(left: ComputationUnit? = null, right: ComputationUnit? = null, operator: ComputationOperator? = null) {
        leftUnit = left
        rightUnit = right
        computationOperator = operator
    }

    val isEmpty: Boolean
        get() = leftUnit == null && rightUnit == null && computationOperator == null

    val isComplete: Boolean
        get() = leftUnit != null && rightUnit != null && computationOperator != null

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("[")
        builder.append(if (leftUnit == null) "null" else leftUnit!!.toString())
        builder.append(if (computationOperator == null)
            " null "
        else
            " " + computationOperator!!.toString() + " ")
        builder.append(if (rightUnit == null) "null" else rightUnit!!.toString())
        builder.append("]")
        return builder.toString()
    }

    override fun clone(): ComputationCompoundUnit {
        return ComputationCompoundUnit(this.leftUnit, this.rightUnit, this.computationOperator)
    }

    override fun computeResult(): BigDecimal {
        if (leftUnit != null && computationOperator == null && rightUnit == null) {
            return leftUnit!!.computeResult()
        } else {
            if (computationOperator == null || rightUnit == null) {
                throw IncompleteComputationCompoundUnitException(
                        "Computation incomplete: ( " + toString() + " )")
            } else {
                return computationOperator!!.compute(leftUnit!!.computeResult(), rightUnit!!.computeResult())
            }
        }
    }
}
