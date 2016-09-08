package utils.formula.parser

import utils.formula.Formula

interface FormulaParser {
    fun parseFormula(expression: String): Formula
}
