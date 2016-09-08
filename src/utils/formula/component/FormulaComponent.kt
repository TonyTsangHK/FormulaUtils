package utils.formula.component

interface FormulaComponent {
    enum class Type constructor(val desc: String) {
        OPERATOR("operator"), NUMBER("number"), VARIABLE("variable"), RUNTIME_VARIABLE("runtimeVariable");

        override fun toString(): String {
            return desc
        }
    }

    val componentType: Type
    val formulaComponentExpression: String
}
