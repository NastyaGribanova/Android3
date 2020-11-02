package com.example.calculator.store

import com.example.calculator.InsufficientDataException

class CalculatorReducer {
    fun reduce(state: CalculatorState, action: CalculatorAction): CalculatorState {
        return when(action) {
            is StartComputation -> state.copy(isLoading = true)
            is ComputationSuccess -> state.copy(isLoading = false, values = action.values)
            is WriteValues -> {
                state.apply {
                    if (lastChangedFields != null && lastChangedFields.first != action.newValue.first) {
                        return state.copy(
                            preLastChangedFields = lastChangedFields,
                            lastChangedFields = action.newValue,
                            values = null,
                            error = null
                        )
                    } else if (preLastChangedFields != null && lastChangedFields?.first == action.newValue.first
                        && preLastChangedFields.first != action.newValue.first
                    ) {
                        return state.copy(
                            preLastChangedFields = preLastChangedFields,
                            lastChangedFields = action.newValue,
                            values = null,
                            error = null
                        )
                    } else {
                        return state.copy(
                            preLastChangedFields = state.preLastChangedFields,
                            lastChangedFields = action.newValue,
                            error = InsufficientDataException,
                            values = null
                        )
                    }
                }
            }
            is ErrorComputation -> state.copy(isLoading = false)
        }
    }
}
