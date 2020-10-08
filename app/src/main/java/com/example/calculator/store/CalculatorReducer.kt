package com.example.calculator.store

class CalculatorReducer {
    fun reduce(state: CalculatorState, action: CalculatorAction): CalculatorState {
        return when(action) {
            is StartComputation -> state.copy(isLoading = true)
            is ComputationSuccess -> state.copy(isLoading = false, values = action.values)
            is WriteValues -> state.copy(isLoading = false, values = null)
            is ErrorComputation -> state.copy(isLoading = false)
        }
    }
}
