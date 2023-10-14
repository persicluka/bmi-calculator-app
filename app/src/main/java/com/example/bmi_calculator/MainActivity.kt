package com.example.bmi_calculator


import android.content.Context
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private lateinit var heightText : EditText
    private lateinit var weightText : EditText
    private lateinit var btnCalculate : Button
    private lateinit var resultText : TextView
    private lateinit var tvBmi : TextView
    private var result : Float = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultText = findViewById(R.id.tvResult)
        tvBmi = findViewById(R.id.tvBmi)
        heightText = findViewById(R.id.etHeight)
        weightText = findViewById(R.id.etWeight)
        btnCalculate = findViewById(R.id.btnCalculate)

        btnCalculate.setOnClickListener() {
            if (heightText.text.isNotEmpty() && weightText.text.isNotEmpty()) {
                getBMI()
                displayResult()
                closeKeyboard()
            } else {
                Toast.makeText(this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getBMI(){
        val height = heightText.text.toString().toFloat()
        val weight = weightText.text.toString().toFloat()
        result = weight / ((height / 100) * (height / 100))
    }

    private fun displayResult() {
        val bmi = String.format("%.2f", result).toFloat()
        resultText.text = bmi.toString()
        var color = 0
        var displayText = ""
        when {
            bmi < 18.50 -> {
                color = R.color.underweight
                displayText = "Underweight"
            }
            bmi in 18.50..24.99 -> {
                color = R.color.healthy
                displayText = "Healthy"
            }
            bmi in 25.00..29.99 -> {
                color = R.color.overweight
                displayText = "Overweight"
            }
            bmi > 30.00 -> {
                color = R.color.obese
                displayText = "Obese"
            }
        }
        btnCalculate.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, color))
        btnCalculate.text = displayText
        resultText.setTextColor(ContextCompat.getColor(this, color))
        tvBmi.setTextColor(ContextCompat.getColor(this, color))
    }

    private fun closeKeyboard() {
        val view = this.currentFocus as View
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}