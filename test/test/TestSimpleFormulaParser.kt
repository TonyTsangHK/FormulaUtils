package test

import java.math.BigDecimal

import org.testng.Assert
import org.testng.annotations.Test
import utils.formula.Formula
import utils.formula.parser.SimpleFormulaParser

class TestSimpleFormulaParser {
    private val simpleFormulaParser = SimpleFormulaParser
    private val defaultDelta = 0.0000001
    @Test
    fun testSimpleExpression() {
        Assert.assertEquals(
            BigDecimal("3").compareTo(
                    simpleFormulaParser.parseFormula("1+1+1").compute()!!
            ), 0
        )

        Assert.assertEquals(
                BigDecimal("6").compareTo(
                        simpleFormulaParser.parseFormula("1+2+3").compute()!!
                ), 0
        )

        Assert.assertEquals(
            simpleFormulaParser.parseFormula("99-6+3-88.6+11.53").compute()!!.toDouble(),
            18.93, defaultDelta
        )
    }

    @Test
    fun testSimplePostfixExpression() {
        Assert.assertEquals(
            simpleFormulaParser.parseFormula("50%").compute()!!.toDouble(),
            0.5, defaultDelta
        )
        Assert.assertEquals(
            simpleFormulaParser.parseFormula("10%").compute()!!.toDouble(),
            0.1, defaultDelta
        )
        Assert.assertEquals(
            simpleFormulaParser.parseFormula("125%").compute()!!.toDouble(),
            1.25, defaultDelta
        )

        Assert.assertEquals(simpleFormulaParser.parseFormula("5!").compute()!!.toInt(), 120)
        Assert.assertEquals(simpleFormulaParser.parseFormula("3!").compute()!!.toInt(), 6)
        Assert.assertEquals(simpleFormulaParser.parseFormula("2!").compute()!!.toInt(), 2)
        Assert.assertEquals(simpleFormulaParser.parseFormula("1!").compute()!!.toInt(), 1)
        Assert.assertEquals(simpleFormulaParser.parseFormula("0!").compute()!!.toInt(), 0)
    }

    @Test
    fun testPrecedence() {
        Assert.assertFalse(
            BigDecimal("9").compareTo(
                    simpleFormulaParser.parseFormula("1+2*3").compute()!!
            ) == 0
        )

        Assert.assertEquals(simpleFormulaParser.parseFormula("1+2*3").compute()!!.intValueExact(), 7)

        Assert.assertFalse(
            BigDecimal("81").compareTo(
                    simpleFormulaParser.parseFormula("(1+2*3^2)").compute()!!
            ) == 0
        )

        Assert.assertEquals(
            simpleFormulaParser.parseFormula("(1+2*3^2)").compute()!!.intValueExact(), 19
        )

        Assert.assertEquals(
            simpleFormulaParser.parseFormula("1+2*3^2").compute()!!.intValueExact(), 19
        )

        Assert.assertEquals(
            simpleFormulaParser.parseFormula("5+4*2^300%!").compute()!!.intValueExact(), 261
        )
    }

    @Test
    fun testBracket() {
        Assert.assertEquals(
            simpleFormulaParser.parseFormula("1+2*(3+1)").compute()!!.intValueExact(), 9
        )

        val formulaExpression = "2^(3+2*(((2-1*(2-1^2^(2-2^4^0.5)))*3)+6*2-7*2))"

        val formula = simpleFormulaParser.parseFormula(formulaExpression)

        Assert.assertEquals(formula.compute()!!.toDouble(), 32.0, defaultDelta)
    }

    @Test
    fun testMixedFormula() {
        var formulaExpression = "2^(3+2*(((2-1*(2-1^2^(2-2^4^50%)))*3)+3!*200%-7*2!))"

        var formula = simpleFormulaParser.parseFormula(formulaExpression)

        Assert.assertEquals(formula.compute()!!.toDouble(), 32.0, defaultDelta)

        formulaExpression = "2^(3+2*(((2-1*(2-1^2^(2-2^(2*100+1*100+2!*100-1*100)%^50%)))*3)+3!*200%-7*2!))"

        formula = simpleFormulaParser.parseFormula(formulaExpression)

        Assert.assertEquals(formula.compute()!!.toDouble(), 32.0, defaultDelta)

        formulaExpression = "2! + 3 - 5! * 2! ^ 200% + 3 ^ 2 - 9 * 50%"

        formula = simpleFormulaParser.parseFormula(formulaExpression)

        Assert.assertEquals(formula.compute()!!.toDouble(), -470.5, defaultDelta)

        formulaExpression = "√16+2!-5!*2!^200%+√(400%)-√√(9^4)*50%"

        formula = simpleFormulaParser.parseFormula(formulaExpression)

        Assert.assertEquals(formula.compute()!!.toDouble(), -476.5, defaultDelta)
    }

    @Test
    fun testSimplePrefixExpression() {
        val formulaExpression = "√√16"

        val formula = simpleFormulaParser.parseFormula(formulaExpression)

        Assert.assertEquals(formula.compute()!!.toDouble(), 2.0, defaultDelta)
    }
}
