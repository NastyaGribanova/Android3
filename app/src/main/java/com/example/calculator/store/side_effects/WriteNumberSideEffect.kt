package com.example.calculator.store.side_effects

import com.example.calculator.*
import com.example.calculator.store.*
import com.freeletics.rxredux.StateAccessor
import com.jakewharton.rxrelay2.Relay
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.ofType
import java.lang.NumberFormatException

class WriteNumberSideEffect (
    private val calculatorApi: CalculatorAPI,
    private val newsRelay: Relay<CalculatorMessage>
) : CalculatorSideEffect {

    private var lastValue: Pair<String, String>? = null
    private var preLastValue: Pair<String, String>? = null

    override fun invoke(
        actions: Observable<CalculatorAction>,
        state: StateAccessor<CalculatorState>
    ): Observable<out CalculatorAction> {
        return actions.ofType<WriteValues>()
            .switchMap { action ->
                getResult(action.newValue)
                    .map<CalculatorAction> {
                        ComputationSuccess(
                            Triple(
                                it.first.toString(),
                                it.second.toString(),
                                it.third.toString()
                            )
                        )
                    }
                    .doOnError { throwable ->
                        when (throwable) {
                            NotSuchCharactersException -> newsRelay.accept(
                                ShowComputationError(
                                    throwable.message.toString()
                                )
                            )
                        }
                    }
                    .onErrorReturnItem(ErrorComputation)
                    .toObservable()
                    .startWith(StartComputation)
            }
    }

    private fun getResult(newValue: Pair<String, String>): Single<Triple<Int, Int, Int>> {
        return if (lastValue != null && lastValue?.first != newValue.first) {
            val mLastValue = lastValue
            preLastValue = lastValue
            lastValue = newValue
            computation(newValue, mLastValue!!)
        } else if (preLastValue != null && lastValue?.first == newValue.first && preLastValue?.first != newValue.first) {
            lastValue = newValue
            computation(newValue, preLastValue!!)
        } else {
            lastValue = newValue
            Single.error(InsufficientDataException)
        }
    }


    private fun computation(
        lastValue: Pair<String, String>,
        preLastValue: Pair<String, String>
    ): Single<Triple<Int, Int, Int>> {
        this.lastValue = lastValue
        this.preLastValue = preLastValue
        try {
            val lastNumber = Integer.parseInt(lastValue.second)
            val preLastNumber = Integer.parseInt(preLastValue.second)
            return when (lastValue.first) {
                Constants.FIRST_VALUE -> {
                    when (preLastValue.first) {
                        Constants.SECOND_VALUE -> {
                            calculatorApi.getSum(
                                lastNumber,
                                preLastNumber
                            )
                        }
                        Constants.SUM -> {
                            calculatorApi.getSecondValue(
                                lastNumber,
                                preLastNumber
                            )
                        }
                        else -> {
                            Single.error(UnexpectedException)
                        }
                    }
                }
                Constants.SECOND_VALUE -> {
                    when (preLastValue.first) {
                        Constants.FIRST_VALUE -> {
                            calculatorApi.getSum(
                                preLastNumber,
                                lastNumber
                            )
                        }
                        Constants.SUM -> {
                            calculatorApi.getFirstValue(
                                lastNumber,
                                preLastNumber
                            )
                        }
                        else -> {
                            Single.error(UnexpectedException)
                        }
                    }
                }
                Constants.SUM -> {
                    when (preLastValue.first) {
                        Constants.FIRST_VALUE -> {
                            calculatorApi.getSecondValue(
                                preLastNumber,
                                lastNumber
                            )
                        }
                        Constants.SECOND_VALUE -> {
                            calculatorApi.getFirstValue(
                                preLastNumber,
                                lastNumber
                            )
                        }
                        else -> {
                            Single.error(UnexpectedException)
                        }
                    }
                }
                else -> {
                    Single.error(UnexpectedException)
                }
            }
        } catch (e: NumberFormatException) {
            return Single.error(NotSuchCharactersException)
        }
    }
}
