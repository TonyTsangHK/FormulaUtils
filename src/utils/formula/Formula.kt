package utils.formula

import utils.formula.component.ComputationNumber
import utils.formula.component.FormulaComponent
import utils.formula.component.RuntimeVariable
import utils.formula.component.Variable
import utils.formula.exception.FormulaProcessingException
import utils.formula.operator.ComputationOperator
import utils.formula.operator.PostfixOperator
import utils.formula.operator.PrefixOperator
import utils.formula.operator.general.LeftBracket
import utils.formula.operator.general.RightBracket
import java.math.BigDecimal
import java.util.*

/**
 * Created with IntelliJ IDEA.
 * User: Tony Tsang
 * Date: 2016-09-08
 * Time: 09:59
 */
class Formula {
    private lateinit var components: MutableList<FormulaComponent>
    private lateinit var chainerStack: Stack<ComputationChainer>
    private var result: BigDecimal? = null

    init {
        components = ArrayList<FormulaComponent>()
        chainerStack = Stack<ComputationChainer>()
        result = null
    }

    fun replaceFormulaComponent(comp: FormulaComponent, vararg replacement: FormulaComponent) {
        val indices = allIndexOfFormulaComponent(comp)

        if (indices.size > 0) {
            for (i in indices.indices.reversed()) {
                for (j in replacement.indices.reversed()) {
                    components.add(indices[i] + 1, replacement[j])
                }
                components.removeAt(indices[i])
            }

            resetResult()
        }
    }

    fun replaceFormulaComponent(comp: FormulaComponent, replacements: Collection<FormulaComponent>) {
        val indices = allIndexOfFormulaComponent(comp)

        if (indices.size > 0) {
            for (i in indices.indices.reversed()) {
                components.addAll(indices[i] + 1, replacements)
                components.removeAt(indices[i])
            }
            resetResult()
        }
    }

    fun insertFormulaComponent(index: Int, comp: FormulaComponent) {
        if (index >= 0 && index < components.size) {
            components.add(index, comp)
            resetResult()
        }
    }

    fun addFormulaComponent(comp: FormulaComponent) {
        components.add(comp)
        resetResult()
    }

    fun indexOfFormulaComponent(comp: FormulaComponent): Int {
        return indexOfFormulaComponent(comp, 0)
    }

    fun indexOfFormulaComponent(comp: FormulaComponent, startIndex: Int): Int {
        for (i in startIndex..components.size - 1) {
            if (components[i] == comp) {
                return i
            }
        }
        return -1
    }

    fun allIndexOfFormulaComponent(comp: FormulaComponent): Array<Int> {
        return allIndexOfFormulaComponent(comp, 0)
    }

    fun allIndexOfFormulaComponent(comp: FormulaComponent, startIndex: Int): Array<Int> {
        val indices = ArrayList<Int>()

        var c = startIndex

        while (c != -1) {
            val i = indexOfFormulaComponent(comp, c)
            if (i == -1) {
                c = i
            } else {
                indices.add(i)
                c = i + 1
            }
        }

        return Array<Int>(
            indices.size, {
                indices[it]
            }
        )
    }

    private fun computeResult(variableValues: Map<String, BigDecimal>?): BigDecimal {
        val rootChainer = ComputationChainer()
        chainerStack.push(rootChainer)

        for (comp in components) {
            val chainer = chainerStack.peek()
            val cType = comp.componentType

            if (cType == FormulaComponent.Type.VARIABLE) {
                chainer.addComputationUnit(comp as Variable)
            } else if (cType == FormulaComponent.Type.RUNTIME_VARIABLE) {
                val variable = comp as RuntimeVariable
                if (variableValues != null && variableValues[variable.expressionName] != null) {
                    chainer.addComputationUnit(
                        ComputationNumber(variableValues[variable.expressionName]!!)
                    )
                } else {
                    throw FormulaProcessingException(
                            "Unknown runtime variable: " + variable.expressionName)
                }
            } else if (cType == FormulaComponent.Type.NUMBER) {
                chainer.addComputationUnit(comp as ComputationNumber)
            } else if (cType == FormulaComponent.Type.OPERATOR) {
                if (comp is LeftBracket) {
                    val newChainer = ComputationChainer()
                    chainerStack.push(newChainer)
                } else if (comp is RightBracket) {
                    val lastChainer = chainerStack.pop()
                    if (chainerStack.isEmpty()) {
                        throw FormulaProcessingException(
                                "utils.formula.Formula syntax error: No matching left bracket found")
                    } else {
                        lastChainer.complete()
                        chainerStack.peek().addComputationUnit(lastChainer)
                    }
                } else if (comp is ComputationOperator) {
                    chainer.addComputationOperator(comp)
                } else if (comp is PostfixOperator) {
                    chainer.addPostfixComputationOperator(comp)
                } else if (comp is PrefixOperator) {
                    chainer.addPrefixComputationOperator(comp)
                }
            } else {
                throw FormulaProcessingException("utils.formula.Formula syntax error")
            }
        }

        return chainerStack.firstElement().computeResult()
    }

    // Compute should not return null
    // if anything goes wrong, consider to throw arithmetic exception instead
    fun compute(): BigDecimal {
        return compute(null)
    }

    // Compute should not return null
    // if anything goes wrong, consider to throw arithmetic exception instead
    fun compute(variableValues: Map<String, BigDecimal>?): BigDecimal {
        if (result != null) {
            return result!!
        } else if (!chainerStack.isEmpty()) {
            result = chainerStack.firstElement().computeResult()
            return result!!
        } else {
            result = computeResult(variableValues)
            return result!!
        }
    }

    fun resetResult() {
        result = null
        chainerStack.clear()
    }

    fun printChainer() {
        compute()

        var c = 0

        chainerStack.forEach {
            v ->
                println("c$c: $v")
                c++
        }
    }

    fun getFormulaComponent(i: Int): FormulaComponent? {
        if (i >= 0 && i < components.size) {
            return components[i]
        } else {
            return null
        }
    }

    fun getFormulaComponentCount(): Int {
        return components.size
    }

    fun getCachedResult(): BigDecimal? {
        return result
    }

    fun isEmpty(): Boolean {
        return components.isEmpty()
    }

    override fun toString(): String {
        val builder = StringBuilder()

        for (comp in components) {
            builder.append(comp.formulaComponentExpression)
        }

        return builder.toString()
    }

    override fun equals(other: Any?): Boolean {
        return other != null && other is Formula && toString() == other.toString()
    }

    override fun hashCode(): Int {
        var h = 0

        for (comp in components) {
            h += 37 * comp.hashCode()
        }

        return h
    }

    fun hasVariable(): Boolean {
        for (comp in components) {
            if (comp is Variable || comp is RuntimeVariable) {
                return true
            }
        }

        return false
    }
}