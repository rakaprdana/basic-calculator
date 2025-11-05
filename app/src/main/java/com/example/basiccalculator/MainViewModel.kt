package com.example.basiccalculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception

class MainViewModel : ViewModel() {
    private val _input = MutableLiveData<String>("0")
    private val _output = MutableLiveData<String>("0")

    val input: LiveData<String> = _input
    val output: LiveData<String> = _output

    fun setInput(value: String) {
        if (_input.value == "0") {
            _input.value = value
        } else {
            _input.value += value
        }
    }

    fun deleteNum() {
        _input.value = if (_input.value?.length == 1) {
            "0"
        } else {
            _input.value?.dropLast(1)
        }
    }

    fun clearAll() {
        _input.value = "0"
        _output.value = "0"
    }

    fun calculate() {
        val expression = _input.value
        try {
            val result = ExpressionBuilder(expression).build().evaluate()
            _output.value = result.toString()
        } catch (e: Exception) {
            return
        }

    }
}