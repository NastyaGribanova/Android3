package com.example.calculator.store.side_effects

import com.example.calculator.store.CalculatorAction
import com.example.calculator.store.CalculatorState
import com.freeletics.rxredux.SideEffect

typealias CalculatorSideEffect = SideEffect<CalculatorState, CalculatorAction>
