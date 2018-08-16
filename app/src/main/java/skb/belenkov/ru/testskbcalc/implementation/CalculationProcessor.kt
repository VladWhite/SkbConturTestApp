package skb.belenkov.ru.testskbcalc.implementation

import android.content.Context
import com.squareup.duktape.DuktapeException
import io.reactivex.Observable
import mathjs.niltonvasques.com.mathjs.MathJS
import skb.belenkov.ru.testskbcalc.R
import skb.belenkov.ru.testskbcalc.interfaces.ICalculationProcessor

class CalculationProcessor(private val context: Context) : ICalculationProcessor {

    override fun getCalculationProcessor(operation: String, displayContent: String): Observable<String> {
        return Observable.create { subscriber ->
            run {
                val math = MathJS()
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
                        var result: String
                        try {
                            result = math.eval(displayContent)
                            if (result.equals(context.getString(R.string.infinity), true)) {
                                subscriber.onNext(context.getString(R.string.divideByZero))
                            }else{
                                subscriber.onNext(result)
                            }
                        } catch (e: DuktapeException) {
                            subscriber.onNext(context.getString(R.string.wrongExpression))
                        }
                    }
                    else -> {
                        subscriber.onNext(displayContent + operation)
                    }
                }
            }
        }
    }
}