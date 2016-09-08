package test

import java.math.BigDecimal

import org.testng.Assert
import org.testng.annotations.Test
import utils.formula.parser.SimpleFormulaParser

class TestSimpleFormulaParser {
    @Test
    fun testSimpleExpression() {
        assertBigDecimalEquals(
            SimpleFormulaParser.parseFormula("1+1+1").compute(), BigDecimal("3")
        )

        assertBigDecimalEquals(
            SimpleFormulaParser.parseFormula("1+2+3").compute(), BigDecimal("6")
        )

        assertBigDecimalEquals(
            SimpleFormulaParser.parseFormula("99-6+3-88.6+11.53").compute(), BigDecimal("18.93")
        )
    }

    @Test
    fun testSimplePostfixExpression() {
        assertBigDecimalEquals(
            SimpleFormulaParser.parseFormula("50%").compute(), BigDecimal("0.5")
        )
        assertBigDecimalEquals(
            SimpleFormulaParser.parseFormula("10%").compute(), BigDecimal("0.1")
        )
        assertBigDecimalEquals(
            SimpleFormulaParser.parseFormula("125%").compute(), BigDecimal("1.25")
        )

        Assert.assertEquals(SimpleFormulaParser.parseFormula("5!").compute().toInt(), 120)
        Assert.assertEquals(SimpleFormulaParser.parseFormula("3!").compute().toInt(), 6)
        Assert.assertEquals(SimpleFormulaParser.parseFormula("2!").compute().toInt(), 2)
        Assert.assertEquals(SimpleFormulaParser.parseFormula("1!").compute().toInt(), 1)
        Assert.assertEquals(SimpleFormulaParser.parseFormula("0!").compute().toInt(), 0)
    }

    @Test
    fun testPrecedence() {
        Assert.assertNotEquals(
            SimpleFormulaParser.parseFormula("1+2*3").compute().toInt(), 9
        )

        Assert.assertEquals(SimpleFormulaParser.parseFormula("1+2*3").compute().toInt(), 7)

        Assert.assertNotEquals(
            SimpleFormulaParser.parseFormula("(1+2*3^2)").compute().toInt(), 81
        )

        Assert.assertEquals(
            SimpleFormulaParser.parseFormula("(1+2*3^2)").compute().toInt(), 19
        )

        Assert.assertEquals(
            SimpleFormulaParser.parseFormula("1+2*3^2").compute().toInt(), 19
        )

        Assert.assertEquals(
            SimpleFormulaParser.parseFormula("5+4*2^300%!").compute().toInt(), 261
        )
    }

    @Test
    fun testBracket() {
        Assert.assertEquals(
            SimpleFormulaParser.parseFormula("1+2*(3+1)").compute().toInt(), 9
        )

        val formulaExpression = "2^(3+2*(((2-1*(2-1^2^(2-2^4^0.5)))*3)+6*2-7*2))"

        val formula = SimpleFormulaParser.parseFormula(formulaExpression)

        assertBigDecimalEquals(formula.compute(), BigDecimal("32.0"))
    }

    @Test
    fun testMixedFormula() {
        var formulaExpression = "2^(3+2*(((2-1*(2-1^2^(2-2^4^50%)))*3)+3!*200%-7*2!))"

        var formula = SimpleFormulaParser.parseFormula(formulaExpression)

        assertBigDecimalEquals(formula.compute(), BigDecimal("32.0"))

        formulaExpression = "2^(3+2*(((2-1*(2-1^2^(2-2^(2*100+1*100+2!*100-1*100)%^50%)))*3)+3!*200%-7*2!))"

        formula = SimpleFormulaParser.parseFormula(formulaExpression)

        assertBigDecimalEquals(formula.compute(), BigDecimal("32.0"))

        formulaExpression = "2! + 3 - 5! * 2! ^ 200% + 3 ^ 2 - 9 * 50%"

        formula = SimpleFormulaParser.parseFormula(formulaExpression)

        assertBigDecimalEquals(formula.compute(), BigDecimal("-470.5"))

        formulaExpression = "√16+2!-5!*2!^200%+√(400%)-√√(9^4)*50%"

        formula = SimpleFormulaParser.parseFormula(formulaExpression)

        assertBigDecimalEquals(formula.compute(), BigDecimal("-476.5"))
    }

    @Test
    fun testSimplePrefixExpression() {
        val formulaExpression = "√√16"

        val formula = SimpleFormulaParser.parseFormula(formulaExpression)

        assertBigDecimalEquals(formula.compute(), BigDecimal("2.0"))
    }

    // Since BigDecimal's equals also compare scale which not ideal for general value comparison
    // This method will use compareTo method for equality checking.
    fun assertBigDecimalEquals(a: BigDecimal, b: BigDecimal) {
        if (a.compareTo(b) != 0) {
            // Recreate the error message with the same format as Assert.assertEquals
            throw AssertionError("expected [$b] but found [$a]")
        }
    }
}
