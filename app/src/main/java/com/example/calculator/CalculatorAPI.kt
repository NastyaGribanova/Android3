package com.example.calculator

import io.reactivex.Single
import java.util.concurrent.TimeUnit

class CalculatorAPI {
    companion object {
        private const val WORK_TIME = 3L
    }

    fun getFirstValue(secondValue: Int, sum: Int): Single<Triple<Int, Int, Int>> {
        return Single.just(Triple((sum - secondValue), secondValue, sum))
            .delay(WORK_TIME, TimeUnit.SECONDS)
    }

    fun getSecondValue(firstValue: Int, sum: Int): Single<Triple<Int, Int, Int>> {
        return Single.just(Triple(firstValue, (sum - firstValue), sum))
            .delay(WORK_TIME, TimeUnit.SECONDS)
    }

    fun getSum(firstTerm: Int, secondTerm: Int): Single<Triple<Int, Int, Int>> {
        return Single.just(Triple(firstTerm, secondTerm, (firstTerm + secondTerm)))
            .delay(WORK_TIME, TimeUnit.SECONDS)
    }
}
