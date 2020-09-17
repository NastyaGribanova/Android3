package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_change_theme.setOnClickListener{
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        setOperation()

    }

    fun setTextFields(str: String){
        if(mathResult.text != ""){
            mathOperation.text = mathResult.text
            mathResult.text = ""
        }
        mathOperation.append(str)
    }

    fun setOperation(){
        number0.setOnClickListener{ setTextFields("0")}
        number1.setOnClickListener{ setTextFields("1")}
        number2.setOnClickListener{ setTextFields("2")}
        number3.setOnClickListener{ setTextFields("3")}
        number4.setOnClickListener{ setTextFields("4")}
        number5.setOnClickListener{ setTextFields("5")}
        number6.setOnClickListener{ setTextFields("6")}
        number7.setOnClickListener{ setTextFields("7")}
        number8.setOnClickListener{ setTextFields("8")}
        number9.setOnClickListener{ setTextFields("9")}
        signPoint.setOnClickListener{ setTextFields(".")}
        signPlus.setOnClickListener{ setTextFields("+")}
        signMinus.setOnClickListener{ setTextFields("-")}
        signMultip.setOnClickListener{ setTextFields("*")}
        signDivision.setOnClickListener{ setTextFields("/")}
        signRight.setOnClickListener{ setTextFields(")")}
        signLeft.setOnClickListener{ setTextFields("(")}
        signAC.setOnClickListener{
            mathOperation.text = ""
            mathResult.text = ""
        }
        btnBack.setOnClickListener {
            val str = mathOperation.text.toString()
            if(str.isNotEmpty()){
                mathOperation.text = str.substring(0, str.length - 1)
            }
            mathResult.text = ""
        }
        signEqual.setOnClickListener {
            try{
                val ex = ExpressionBuilder(mathOperation.text.toString()).build()
                val result = ex.evaluate()

                val longResult = result.toLong()
                if(result == longResult.toDouble())
                    mathResult.text = longResult.toString()
                else
                    mathResult.text = result.toString()
            } catch (e:Exception) {
                Log.d("Error", "message: ${e.message}")
            }
        }

    }
}
