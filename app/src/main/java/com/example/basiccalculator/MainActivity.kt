package com.example.basiccalculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    private val tvInput by lazy { findViewById<TextView>(R.id.tv_input) }
    private val tvOutput by lazy { findViewById<TextView>(R.id.tv_output) }
    private val btnDel by lazy { findViewById<Button>(R.id.btn_del) }
    private val btnClear by lazy { findViewById<Button>(R.id.btn_clear) }
    private val btnCalculate by lazy { findViewById<Button>(R.id.btn_calculate) }

    private val buttons: List<Button> by lazy {
        val buttonIds = listOf(
            R.id.btn_0,
            R.id.btn_1,
            R.id.btn_2,
            R.id.btn_3,
            R.id.btn_4,
            R.id.btn_5,
            R.id.btn_6,
            R.id.btn_7,
            R.id.btn_8,
            R.id.btn_9,
            R.id.btn_koma,
            R.id.btn_plus,
            R.id.btn_minus
        )
        buttonIds.map { findViewById<Button>(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        observerInputOutput(viewModel.input)
        observerInputOutput(viewModel.output)
        btnDel.setOnClickListener {
            viewModel.deleteNum()
        }
        buttons.forEach { button -> button.setClickListener(viewModel) }
        btnClear.setOnClickListener { viewModel.clearAll() }
        btnCalculate.setOnClickListener { viewModel.calculate() }
    }

    private fun Button.setClickListener(viewModel: MainViewModel) {
        this.setOnClickListener {
            val value = this.text.toString()
            viewModel.setInput(value)
        }
    }

    private fun observerInputOutput(value: LiveData<String>) {
        if (value == viewModel.input) {
            viewModel.input.observe(this) { input -> tvInput.text = input }
        } else {
            viewModel.output.observe(this) { output -> tvOutput.text = output }

        }
    }
}