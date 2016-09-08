package utils.formula.component

interface RuntimeVariable : FormulaComponent, ComputationUnit {
    val expressionName: String
}
