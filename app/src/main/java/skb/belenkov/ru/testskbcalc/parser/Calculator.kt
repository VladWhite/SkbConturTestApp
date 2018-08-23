package skb.belenkov.ru.testskbcalc.parser

class Calculator : ICalculator {
    private lateinit var lexer: Lexer

    private val WRONG_EXPRESSION = "неверное выражение"
    private val DIVIDE_BY_ZERO= "деление на ноль"
    private val INFINITY= "Infinity"

    private fun primary(): Double {
        var result: Double
        val curType = lexer.currentLexem.type

        when (curType) {
            LexemType.OPEN -> {
                lexer.nextLexem()
                result = expression()
                lexer.nextLexem()
            }
            LexemType.NUMBER -> {
                result = lexer.currentLexem.value
                lexer.nextLexem()
            }
            else -> result = expression()
        }
        return result
    }


    private fun power(): Double {
        var result = primary()
        val curType = lexer.currentLexem.type

        if (curType === LexemType.POW) {
            lexer.nextLexem()
            result = Math.pow(result, unary())
        }
        return result
    }

    private fun unary(): Double {
        val result: Double
        val curType = lexer.currentLexem.type

        result = when (curType) {
            LexemType.MINUS -> {
                lexer.nextLexem()
                -power()
            }
            LexemType.PLUS -> {
                lexer.nextLexem()
                power()
            }
            else -> power()
        }
        return result
    }

    private fun term(): Double {
        var result = unary()
        var curType = lexer.currentLexem.type

        while (curType === LexemType.MULT || curType === LexemType.DIV) {
            lexer.nextLexem()
            if (curType === LexemType.MULT)
                result *= unary()
            else
                result /= unary()
            curType = lexer.currentLexem.type
        }
        return result
    }

    private fun expression(): Double {
        var result = term()
        var curType = lexer.currentLexem.type

        while (curType === LexemType.PLUS || curType === LexemType.MINUS) {
            lexer.nextLexem()
            if (curType === LexemType.PLUS)
                result += term()
            else
                result -= term()
            curType = lexer.currentLexem.type
        }
        return result
    }

    override fun calculate(expression: String): String {
        lexer = Lexer(expression.toLowerCase())
        lexer.nextLexem()
        return try {
            val result = expression().toString()
            println(result)
            if(result == INFINITY)
                DIVIDE_BY_ZERO
            else
                result
        }catch (e: Throwable){
            WRONG_EXPRESSION
        }
    }
}