package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.example.calculator.store.*
import com.example.calculator.store.side_effects.WriteNumberSideEffect
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG_PROGRAMMATICALLY = "programmatically"
        private const val TAG_EMPTY = ""
    }

    private val relay = PublishRelay.create<CalculatorMessage>()

    private val store =
        CalculatorStore(
            listOf(WriteNumberSideEffect(CalculatorAPI(), relay)),
            relay
        )

    private var TAG: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        store.state
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::renderPage)

        store.newsRelay
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::renderNews)

        setupTextChangedListeners()
    }

    private fun renderPage(state: CalculatorState) {
        state.values?.let {
            TAG = TAG_PROGRAMMATICALLY

            if (et_field1.text.toString() != it.first) {
                et_field1.setText(it.first)
            }
            if (et_field2.text.toString() != it.second) {
                et_field2.setText(it.second)
            }
            et_field3.setText(it.third.toString())

            TAG = TAG_EMPTY
        }

        progressBar.isVisible = state.isLoading
    }

    private fun renderNews(news: CalculatorMessage) {
        when (news) {
            is ShowComputationError -> buildAlertDialog(news.error)
        }
    }

    private fun setupTextChangedListeners() {
        et_field1.doAfterTextChanged { text ->
            updateValues(Pair(Constants.FIRST_VALUE, text.toString()))
        }

        et_field2.doAfterTextChanged { text ->
            updateValues(Pair(Constants.SECOND_VALUE, text.toString()))
        }

        et_field3.doAfterTextChanged { text ->
            updateValues(Pair(Constants.SUM, text.toString()))
        }
    }

    private fun updateValues(newValue: Pair<String, String>) {
        if (TAG != TAG_PROGRAMMATICALLY) {
            store.actionRelay.onNext(
                WriteValues(newValue)
            )
        }
    }

    private fun buildAlertDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle(R.string.error_title)
            .setMessage(message)
            .setPositiveButton(R.string.alert_dialog_positive_button) { _, _ -> }
            .show()
    }
}