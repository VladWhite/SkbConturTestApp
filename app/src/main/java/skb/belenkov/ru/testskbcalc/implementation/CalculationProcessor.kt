package skb.belenkov.ru.testskbcalc.implementation

import android.content.Context
import io.reactivex.Observable
import skb.belenkov.ru.testskbcalc.R
import skb.belenkov.ru.testskbcalc.interfaces.ICalculationProcessor
import skb.belenkov.ru.testskbcalc.parser.Calculator
import skb.belenkov.ru.testskbcalc.parser.ICalculator

class CalculationProcessor(private val context: Context,
                           private val calculator:ICalculator = Calculator()) : ICalculationProcessor {

    override fun getCalculationProcessor(operation: String, displayContent: String): Observable<String> {
        return Observable.create { subscriber ->
            run {
                when (operation) {
                    context.getString(R.string.plus), context.getString(R.string.mul), context.getString(R.string.div), context.getString(R.string.pow), context.getString(R.string.dot) -> {
                        if (displayContent.isNotEmpty()) {
                            val lastChar = displayContent.last().toString()
                            if (lastChar != context.getString(R.string.plus)
                                    && lastChar != context.getString(R.string.mul)
                                    && lastChar != context.getString(R.string.div)
                                    && lastChar != context.getString(R.string.minus)
                                    && lastChar != context.getString(R.string.pow)
                                    && lastChar != context.getString(R.string.dot))
                                subscriber.onNext(displayContent + operation)
                        }
                    }
                    context.getString(R.string.minus) -> {
                        if (displayContent.isNotEmpty()) {
                            val lastChar = displayContent.last().toString()
                            if (lastChar != context.getString(R.string.minus)) {
                                subscriber.onNext(displayContent + operation)
                            }
                        } else {
                            subscriber.onNext(operation)
                        }
                    }
                    context.getString(R.string.cancel) -> subscriber.onNext(context.getString(R.string.empty))
                    context.getString(R.string.clear) -> {
                        val lenght = displayContent.length
                        subscriber.onNext(displayContent.substring(0, lenght - 1))
                    }
                    context.getString(R.string.equals) -> {
                        val result = calculator.calculate(displayContent)
                        subscriber.onNext(result)
                    }
                    else -> {
                        subscriber.onNext(displayContent + operation)
                    }
                }
            }
        }
    }
}