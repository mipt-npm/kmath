package kscience.kmath.estree.internal

import kscience.kmath.estree.internal.astring.generate
import kscience.kmath.estree.internal.estree.*
import kscience.kmath.expressions.Expression
import kscience.kmath.expressions.Symbol

internal class JSBuilder<T>(val bodyCallback: JSBuilder<T>.() -> BaseExpression) {
    private class GeneratedExpression<T>(val executable: dynamic, val constants: Array<dynamic>) : Expression<T> {
        @Suppress("UNUSED_VARIABLE")
        override fun invoke(arguments: Map<Symbol, T>): T {
            val e = executable
            val c = constants
            val a = js("{}")
            arguments.forEach { (key, value) -> a[key.identity] = value }
            return js("e(c, a)").unsafeCast<T>()
        }
    }

    val instance: Expression<T> by lazy {
        val node = Program(
            sourceType = "script",
            VariableDeclaration(
                kind = "var",
                VariableDeclarator(
                    id = Identifier("executable"),
                    init = FunctionExpression(
                        params = arrayOf(Identifier("constants"), Identifier("arguments")),
                        body = BlockStatement(ReturnStatement(bodyCallback())),
                    ),
                ),
            ),
        )

        eval(generate(node))
        GeneratedExpression(js("executable"), constants.toTypedArray())
    }

    private val constants = mutableListOf<Any>()
    private val keys = mutableListOf<String>()

    fun constant(value: Any?) = when {
        value == null || jsTypeOf(value) == "number" || jsTypeOf(value) == "string" || jsTypeOf(value) == "boolean" -> {
            SimpleLiteral(value)
        }

        else -> {
            val idx = if (value in constants) constants.indexOf(value) else constants.also { it += value }.lastIndex

            MemberExpression(
                computed = true,
                optional = false,
                `object` = Identifier("constants"),
                property = SimpleLiteral(idx),
            )
        }
    }

    fun variable(name: String): BaseExpression = call(getOrFail, Identifier("arguments"), SimpleLiteral(name))

    fun call(function: Function<T>, vararg args: BaseExpression): BaseExpression = SimpleCallExpression(
        optional = false,
        callee = constant(function),
        *args,
    )

    private companion object {
        @Suppress("UNUSED_VARIABLE")
        val getOrFail: (`object`: dynamic, key: String) -> dynamic = { `object`, key ->
            val k = key
            val o = `object`

            if (!(js("k in o") as Boolean))
                throw NoSuchElementException("Key $key is missing in the map.")

            js("o[k]")
        }
    }
}
