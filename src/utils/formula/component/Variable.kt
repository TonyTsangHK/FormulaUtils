package utils.formula.component

import java.math.BigDecimal

interface Variable : FormulaComponent, ComputationUnit {
    val expressionName: String
    val value: BigDecimal
}
