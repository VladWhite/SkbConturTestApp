package skb.belenkov.ru.testskbcalc

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import skb.belenkov.ru.testskbcalc.implementation.CalculationProcessor
import skb.belenkov.ru.testskbcalc.interfaces.ICalculationProcessor

/**
 * @author Belenkov Vladislav
 * @date 14.08.18
 *
 * Здесь тестируем реализацию вычислительного процессора.
 * Если возвращаемое значение не совпадет с необходимым - тест завалится:)
 *
 * */

@RunWith(AndroidJUnit4::class)
class CalculationTest {
    @Test
    fun calculationTest() {
        val appContext = InstrumentationRegistry.getTargetContext()
        val expressionToCalculate = "10+66/(12-6)+45.6"
        val calcProcessor: ICalculationProcessor = CalculationProcessor(appContext)
        calcProcessor.getCalculationProcessor("=", expressionToCalculate)
                .test()
                .assertValue("66.6")
    }
}