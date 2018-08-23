package skb.belenkov.ru.testskbcalc.parser

class Lexem(val type: LexemType) {

    var value: Double = 0.toDouble()
        get() = if (type == LexemType.NUMBER)
            field
        else
            0.0
}