package com.example.calculator.store

sealed class CalculatorMessage
    class ShowComputationError(val error: String) : CalculatorMessage()

