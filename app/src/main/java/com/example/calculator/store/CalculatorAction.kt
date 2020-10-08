package com.example.calculator.store

sealed class CalculatorAction
    class WriteValues(val newValue: Pair<String, String>) : CalculatorAction()

    class ComputationSuccess(val values: Triple<String, String, String>) : CalculatorAction()

    object StartComputation : CalculatorAction()

    object ErrorComputation : CalculatorAction()

