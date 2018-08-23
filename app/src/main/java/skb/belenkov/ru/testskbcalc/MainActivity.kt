package skb.belenkov.ru.testskbcalc

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import skb.belenkov.ru.testskbcalc.implementation.CalculationProcessor
import skb.belenkov.ru.testskbcalc.interfaces.ICalculationProcessor

class MainActivity : AppCompatActivity() {

    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            displayTextView.text = savedInstanceState.getString(getString(R.string.dispayText))
        }
        val calcProcessor: ICalculationProcessor = CalculationProcessor(this)
        getButtonEvent(button1)
                .mergeWith(getButtonEvent(button2))
                .mergeWith(getButtonEvent(button3))
                .mergeWith(getButtonEvent(button4))
                .mergeWith(getButtonEvent(button5))
                .mergeWith(getButtonEvent(button6))
                .mergeWith(getButtonEvent(button7))
                .mergeWith(getButtonEvent(button8))
                .mergeWith(getButtonEvent(button9))
                .mergeWith(getButtonEvent(button0))
                .mergeWith(getButtonEvent(buttonPlus))
                .mergeWith(getButtonEvent(buttonMinus))
                .mergeWith(getButtonEvent(buttonMultiple))
                .mergeWith(getButtonEvent(buttonDivide))
                .mergeWith(getButtonEvent(buttonCancel))
                .mergeWith(getButtonEvent(buttonEquals))
                .mergeWith(getButtonEvent(buttonPow))
                .mergeWith(getButtonEvent(buttonLeftBkt))
                .mergeWith(getButtonEvent(buttonRightBkt))
                .mergeWith(getButtonEvent(buttonDot))
                .mergeWith(getButtonEvent(buttonClear))
                .subscribeBy(
                        onNext = {
                            calcProcessor.getCalculationProcessor(it, displayTextView.text.toString())
                                    .subscribeOn(Schedulers.computation())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeBy(
                                            onNext = {
                                                displayTextView.text = it
                                            }
                                    )
                        },
                        onError = { Toast.makeText(this, it.message, Toast.LENGTH_LONG) })
    }

    /**
     * Здесь сохраняем значение дисплея на текущий момент,
     * чтобы восстановить дисплей в случае поворота
     *  @param outState - текущий бандл
     */
    override fun onSaveInstanceState(outState: Bundle?) {
        outState!!.putString(getString(R.string.dispayText), displayTextView.text.toString())
        super.onSaveInstanceState(outState)
    }

    /**
     * Метод, автоматизирующий регистрацию Observable,
     *  в котором будут слушаться кнопки
     *  @param view - получаемый ui элемент(кнопка)
     */
    @SuppressLint("ClickableViewAccessibility")
    @TargetApi(Build.VERSION_CODES.M)
    private fun getButtonEvent(view: View): Observable<String> {
        return Observable.create { subscriber ->
            run {
                view.setOnClickListener {
                    val button = view as Button
                    button.setOnTouchListener { _, event ->
                        if (event.action == MotionEvent.ACTION_UP) {
                            button.setBackgroundColor(getColor(R.color.colorLightBlue))
                        } else if (event.action == MotionEvent.ACTION_DOWN) {
                            button.setBackgroundColor(getColor(R.color.colorDarkBlue))
                        }
                        false
                    }
                    subscriber.onNext(button.text.toString())
                }
            }
        }
    }
}
