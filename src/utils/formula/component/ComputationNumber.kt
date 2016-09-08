package utils.formula.component

import java.math.BigDecimal

class ComputationNumber:Number, FormulaComponent, ComputationUnit, Comparable<Number> {
    override val componentType: FormulaComponent.Type
        get() = FormulaComponent.Type.NUMBER
    override val formulaComponentExpression: String
        get() = number.toString()

    lateinit var number: BigDecimal
        private set

    constructor(n: Double): this(BigDecimal(n.toString()))

    constructor(n: BigDecimal) {
        this.number = n
    }

    constructor(str: String) {
        this.number = BigDecimal(str)
    }

    override fun toString(): String {
        return number.toString()
    }

    override fun computeResult(): BigDecimal {
        return number
    }

    override fun clone(): ComputationNumber {
        return ComputationNumber(number)
    }

    override fun equals(other: Any?): Boolean {
        if (other is ComputationNumber) {
            return other.number.compareTo(number) == 0
        }
        return false
    }

    override fun hashCode(): Int {
        return toString().hashCode()
    }

    override fun compareTo(other: Number): Int {
        return number.compareTo(BigDecimal(other.toString()))
    }

    override fun toInt(): Int {
        return number.toInt()
    }

    override fun toLong(): Long {
        return number.toLong()
    }

    override fun toFloat(): Float {
        return number.toFloat()
    }

    override fun toDouble(): Double {
        return number.toDouble()
    }

    override fun toByte(): Byte {
        return number.toByte()
    }

    override fun toChar(): Char {
        return number.toChar()
    }

    override fun toShort(): Short {
        return number.toShort()
    }
}
