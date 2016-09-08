package utils.formula.component

import java.math.BigDecimal

import utils.formula.exception.IncompleteComputationCompoundUnitException
import utils.formula.operator.SingleComputationOperator

class ComputationSingleUnit : ComputationUnit {
    var computationUnit: ComputationUnit?
    var operator: SingleComputationOperator?

    @JvmOverloads
    constructor(unit: ComputationUnit? = null, operator: SingleComputationOperator? = null) {
        this.computationUnit = unit
        this.operator = operator
    }

    val isEmpty: Boolean
        get() = computationUnit == null && operator == null

    val isComplete: Boolean
        get() = computationUnit != null && operator != null

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("[")
        builder.append(if (computationUnit == null) "null" else computationUnit!!.toString())
        builder.append(if (operator == null)
            " null "
        else
            " " + operator!!.toString())
        builder.append("]")
        return builder.toString()
    }

    override fun clone(): ComputationSingleUnit {
        return ComputationSingleUnit(this.computationUnit, this.operator)
    }

    override fun computeResult(): BigDecimal {
        if (computationUnit != null && operator == null) {
            return computationUnit!!.computeResult()
        } else {
            if (!isComplete) {
                throw IncompleteComputationCompoundUnitException(
                        "Computation incomplete: ( " + toString() + " )")
            } else {
                return operator!!.compute(computationUnit!!.computeResult())
            }
        }
    }
}
