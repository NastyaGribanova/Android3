package com.example.calculator.store

import com.example.calculator.store.side_effects.CalculatorSideEffect
import com.jakewharton.rxrelay2.Relay
import com.freeletics.rxredux.reduxStore
import io.reactivex.subjects.PublishSubject

class CalculatorStore (
    sideEffect: List<CalculatorSideEffect>,
    val newsRelay: Relay<CalculatorMessage>
) {

    val actionRelay = PublishSubject.create<CalculatorAction>()

    val state = actionRelay.reduxStore(
        CalculatorState(),
        sideEffect,
        CalculatorReducer()::reduce
    )
}
