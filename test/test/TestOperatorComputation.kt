package test

import java.math.BigDecimal

import org.testng.Assert
import org.testng.annotations.Test
import utils.formula.operator.computation.DivisionOperator
import utils.formula.operator.computation.MinusOperator
import utils.formula.operator.computation.MultiplyOperator
import utils.formula.operator.computation.PlusOperator
import utils.formula.operator.computation.PowerOperator
import utils.formula.operator.postfix.PercentageOperator
import utils.formula.operator.postfix.PermutationOperator

class TestOperatorComputation {
    private val defaultDelta = 0.0000001

    @Test
    fun testAdd() {
        val operator = PlusOperator
        Assert.assertEquals(
            operator.compute(BigDecimal("1.7645"), BigDecimal("3.2355")).toDouble(),
            5.0, defaultDelta
        )

        Assert.assertEquals(operator.compute(BigDecimal("10")).toInt(), 10)
    }

    @Test
    fun testSubtract() {
        val operator = MinusOperator

        Assert.assertEquals(
            operator.compute(BigDecimal("100.6"), BigDecimal("11.9")).toDouble(),
            88.7, defaultDelta
        )

        Assert.assertEquals(operator.compute(BigDecimal(10)).toInt(), -10)
    }

    @Test
    fun testMultiply() {
        val operator = MultiplyOperator

        Assert.assertEquals(
            operator.compute(BigDecimal("3.8"), BigDecimal("2.5")).toDouble(),
            9.5, defaultDelta
        )

        Assert.assertEquals(1, operator.compute(BigDecimal("1"), BigDecimal("1")).toInt())

        Assert.assertEquals(0, operator.compute(BigDecimal("6332"), BigDecimal("0")).toInt())
    }

    @Test
    fun testDivide() {
        val operator = DivisionOperator

        Assert.assertEquals(
            operator.compute(BigDecimal("0"), BigDecimal("45223333")).toInt(), 0
        )

        Assert.assertEquals(
            operator.compute(BigDecimal("61.275"), BigDecimal("0.05")).toDouble(),
            1225.5, defaultDelta
        )

        Assert.assertEquals(
            operator.compute(BigDecimal("98.7"), BigDecimal("98.7")).toDouble(),
            1.0, defaultDelta
        )
    }

    @Test
    fun testPower() {
        val operator = PowerOperator

        Assert.assertEquals(
            operator.compute(BigDecimal("3"), BigDecimal("3")).toDouble(),
            27.0, defaultDelta
        )

        Assert.assertEquals(
            operator.compute(BigDecimal("49"), BigDecimal("0.5")).toDouble(),
            7.0, defaultDelta
        )
    }

    @Test
    fun testPercentage() {
        val operator = PercentageOperator

        Assert.assertEquals(operator.compute(BigDecimal(50)).toDouble(), 0.5, defaultDelta)

        Assert.assertEquals(operator.compute(BigDecimal(10)).toDouble(), 0.1, defaultDelta)
    }

    @Test
    fun testPermutation() {
        val operator = PermutationOperator

        Assert.assertEquals(operator.compute(BigDecimal(0)).toInt(), 0)
        Assert.assertEquals(operator.compute(BigDecimal(1)).toInt(), 1)
        Assert.assertEquals(operator.compute(BigDecimal(2)).toInt(), 2)
        Assert.assertEquals(operator.compute(BigDecimal(3)).toInt(), 6)
        Assert.assertEquals(operator.compute(BigDecimal(4)).toInt(), 24)
        Assert.assertEquals(operator.compute(BigDecimal(5)).toInt(), 120)
        Assert.assertEquals(operator.compute(BigDecimal(6)).toInt(), 720)
        Assert.assertEquals(operator.compute(BigDecimal(7)).toInt(), 5040)
    }
}
