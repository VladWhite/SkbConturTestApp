package skb.belenkov.ru.testskbcalc.parser

import java.util.regex.Matcher
import java.util.regex.Pattern

class Lexer internal constructor(private val expression: String) {

    private val doubleRegExp = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?")
    private val matcher: Matcher

    var currentLexem = Lexem(LexemType.OTHER)
    private var it = 0

    init {
        this.matcher = doubleRegExp.matcher(expression)
    }

    internal fun nextLexem() {
        if (it >= expression.length) {
            this.currentLexem = Lexem(LexemType.OTHER)
            return
        }
        when (expression[it]) {
            '+' -> {
                ++it
                currentLexem = Lexem(LexemType.PLUS)
            }
            '-' -> {
                ++it
                currentLexem = Lexem(LexemType.MINUS)
            }
            '*' -> {
                ++it
                currentLexem = Lexem(LexemType.MULT)
            }
            '/' -> {
                ++it
                currentLexem = Lexem(LexemType.DIV)
            }
            '^' -> {
                ++it
                currentLexem = Lexem(LexemType.POW)
            }
            '(' -> {
                ++it
                currentLexem = Lexem(LexemType.OPEN)
            }
            ')' -> {
                ++it
                currentLexem = Lexem(LexemType.CLOSE)
            }
            else -> {
                matcher.find()
                this.currentLexem = Lexem(LexemType.NUMBER)
                currentLexem.value = Math.abs(matcher.group().toDouble())
                it = matcher.end()
            }
        }
    }
}