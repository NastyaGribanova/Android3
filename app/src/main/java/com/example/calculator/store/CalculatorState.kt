package com.example.calculator.store

data class CalculatorState (
    val isLoading: Boolean = false,
    val values: Triple<String, String, String>? = null
)
