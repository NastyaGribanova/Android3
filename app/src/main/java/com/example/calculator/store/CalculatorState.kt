package com.example.calculator.store

data class CalculatorState (
    val isLoading: Boolean = false,
    val values: Triple<String, String, String>? = null,
    val preLastChangedFields: Pair<String, String>? = null,
    val lastChangedFields: Pair<String, String>? = null,
    val error: Exception? = null
)
