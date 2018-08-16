package skb.belenkov.ru.testskbcalc.interfaces

import io.reactivex.Observable
/**
 * @author Belenkov Vladislav
 * @date 14.08.18
 *
 * Интерфейс ICalculationProcessor описывает метод,
 * преобразующий строковое значение с экрана калькулятора
 * в математическое выражение, вычисляет результат и выдает подписчику
 *
 * */
interface ICalculationProcessor {

    /**
     * Описание будущей реализации вычислительного процессора
     * @param operation - получаемая операция или вводимое число на вход
     * @param displayContent - строковое значение с дисплея калькулятора
     * @return Observable<String> - передает подписчику обработанное решение выражение с дисплея
     *
     * */
    fun getCalculationProcessor(operation:String, displayContent:String):Observable<String>
}