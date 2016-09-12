package test

import java.math.BigDecimal

import org.testng.Assert
import org.testng.annotations.Test
import utils.formula.exception.NonExtendableException
import utils.formula.parser.SimpleFormulaParser

class TestSimpleFormulaParser {
    @Test
    fun testSimpleExpression() {
        evaluateFormulaAndVerifyResult("1+1+1", "3")
        evaluateFormulaAndVerifyResult("1+2+3", "6")
        evaluateFormulaAndVerifyResult("99-6+3-88.6+11.53", "18.93")
    }

    @Test
    fun testSimplePostfixExpression() {
        evaluateFormulaAndVerifyResult("50%", "0.5")
        evaluateFormulaAndVerifyResult("10%", "0.1")
        evaluateFormulaAndVerifyResult("125%", "1.25")
        evaluateFormulaAndVerifyResult("5!", BigDecimal("120"))
        evaluateFormulaAndVerifyResult("3!", "6")
        evaluateFormulaAndVerifyResult("2!", "2")
        evaluateFormulaAndVerifyResult("1!", "1")
        evaluateFormulaAndVerifyResult("0!", "1")
    }

    @Test
    fun testFactorial() {
        evaluateFormulaAndVerifyResult("0!", "1")
        evaluateFormulaAndVerifyResult("1!", "1")
        evaluateFormulaAndVerifyResult("2!", "2")
        evaluateFormulaAndVerifyResult("3!", "6")
        evaluateFormulaAndVerifyResult("4!", "24")

        var thrownException: Exception? = null

        try {
            SimpleFormulaParser.parseFormula("-2!").compute()
        } catch (e: Exception) {
            thrownException = e
        }

        Assert.assertTrue(thrownException != null && thrownException is NonExtendableException)

        thrownException = null

        try {
            SimpleFormulaParser.parseFormula("2.22!").compute()
        } catch (e: Exception) {
            thrownException = e
        }

        Assert.assertTrue(thrownException != null && thrownException is NonExtendableException)
    }

    @Test
    fun testSimpleModuloExpression() {
        evaluateFormulaAndVerifyResult("1mod1", "0")
        evaluateFormulaAndVerifyResult("1mod3", "1")
        evaluateFormulaAndVerifyResult("3mod2", "1")
        evaluateFormulaAndVerifyResult("137mod7", "4")
        evaluateFormulaAndVerifyResult("25mod5", "0")
    }

    @Test
    fun testPrecedence() {
        evaluateFormulaAndVerifyResultNotEquals("1+2*3", "9")
        evaluateFormulaAndVerifyResult("1+2*3", "7")
        evaluateFormulaAndVerifyResultNotEquals("(1+2*3^2)", "81")
        evaluateFormulaAndVerifyResult("(1+2*3^2)", "19")
        evaluateFormulaAndVerifyResult("1+2*3^2", "19")
        evaluateFormulaAndVerifyResult("5+4*2^300%!", "261")
        evaluateFormulaAndVerifyResult("5+3*17mod31", "25")
    }

    @Test
    fun testMinusAndNegate() {
        evaluateFormulaAndVerifyResult("-2", "-2")
        evaluateFormulaAndVerifyResult("1--2", "3")
        evaluateFormulaAndVerifyResult("1-2", "-1")
        evaluateFormulaAndVerifyResult("-3^2", "9")
        evaluateFormulaAndVerifyResult("-(3^2)", "-9")
    }

    @Test
    fun testBracket() {
        evaluateFormulaAndVerifyResult("1+2*(3+1)", BigDecimal("9"))
        evaluateFormulaAndVerifyResult("2^(3+2*(((2-1*(2-1^2^(2-2^4^0.5)))*3)+6*2-7*2))", BigDecimal("32.0"))
    }

    @Test
    fun testMixedFormula() {
        evaluateFormulaAndVerifyResult("2^(3+2*(((2-1*(2-1^2^(2-2^4^50%)))*3)+3!*200%-7*2!))", "32.0")
        evaluateFormulaAndVerifyResult("2^(3+2*(((2-1*(2-1^2^(2-2^(2*100+1*100+2!*100-1*100)%^50%)))*3)+3!*200%-7*2!))", "32.0")
        evaluateFormulaAndVerifyResult("2! + 3 - 5! * 2! ^ 200% + 3 ^ 2 - 9 * 50%", "-470.5")
        evaluateFormulaAndVerifyResult("√16+2!-5!*2!^200%+√(400%)-√√(9^4)*50%", "-476.5")
    }

    @Test
    fun testSimplePrefixExpression() {
        evaluateFormulaAndVerifyResult("√√16", "2.0")
    }

    fun evaluateFormulaAndVerifyResult(formulaExpression: String, expectedDecimalExpression: String) {
        evaluateFormulaAndVerifyResult(formulaExpression, BigDecimal(expectedDecimalExpression))
    }

    fun evaluateFormulaAndVerifyResult(formulaExpression: String, expected: BigDecimal) {
        assertBigDecimalEquals(
            SimpleFormulaParser.parseFormula(formulaExpression).compute(), expected
        )
    }

    fun evaluateFormulaAndVerifyResultNotEquals(formulaExpression: String, expectedDecimalExpression: String) {
        evaluateFormulaAndVerifyResultNotEquals(formulaExpression, BigDecimal(expectedDecimalExpression))
    }

    fun evaluateFormulaAndVerifyResultNotEquals(formulaExpression: String, expected: BigDecimal) {
        val result = SimpleFormulaParser.parseFormula(formulaExpression).compute()

        if (expected.compareTo(result) == 0) {
            throw AssertionError("expected not equals but not, expected: [$expected] actual: [$result]")
        }
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
