package com.abdalllahyascer.HistoryCalc

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculatorViewModel :ViewModel() {
    var state by mutableStateOf(CalculatorState())
    var lastAns :Double?=null

    fun onAction(action: CalculatorAction) {
        when(action) {
            is CalculatorAction.Number -> enterNumber(action.number)
            is CalculatorAction.Delete -> delete()
            is CalculatorAction.Clear -> state = CalculatorState()
            is CalculatorAction.Operation -> enterOperation(action.operation)
            is CalculatorAction.Decimal -> enterDecimal()
            is CalculatorAction.Calculate -> calculate()
            is CalculatorAction.LastAns -> lastAns()
            is CalculatorAction.Negative -> negative()
            is CalculatorAction.PI -> pi()
            is CalculatorAction.Percentage -> percentage()
        }
    }

    private fun lastAns() {
        if (lastAns==null)
            return
        else{
            if (state.operation != null) {
                state = if (state.number2.contains("ans")) {
                    state.copy(number2 = state.number2.replace("ans", ""))
                } else {
                    state.copy(number2 = state.number2+ "ans" )
                }
                return
            }
            state = if (state.number1.contains("ans")){
                state.copy(number1 = state.number1.replace("ans",""))
            }else{
                state.copy(number1 = state.number1+"ans")
            }
        }
    }
    private fun calculate() {

        val number1 = stringToDouble(state.number1)
        val number2 = stringToDouble(state.number2)
        if(number1 != null && number2 != null) {
            val result = when(state.operation) {
                is CalculatorOperation.Add -> number1 + number2
                is CalculatorOperation.Subtract -> number1 - number2
                is CalculatorOperation.Multiply -> number1 * number2
                is CalculatorOperation.Divide -> number1 / number2
                null -> return
            }
            state = state.copy(
                number1 = result.toString().take(15),
                number2 = "",
                operation = null
            )
            lastAns=result
        }
    }
    private fun negative() {
        if (state.operation != null) {
            state = if (state.number2.contains("-")) {
                state.copy(number2 = state.number2.replace("-", ""))
            } else {
                state.copy(number2 = "-" + state.number2)
            }
            return
        }
        state = if (state.number1.contains("-")){
            state.copy(number1 = state.number1.replace("-",""))
        }else{
            state.copy(number1 = "-"+state.number1)
        }
    }
    private fun enterOperation(operation: CalculatorOperation) {
        if(state.number1.isNotBlank()) {
            state = state.copy(operation = operation)
        }else{
            state = state.copy(number1 = "Ans", operation = operation)
        }
    }
    private fun delete() {
        when {
            state.number2.isNotBlank() -> state = state.copy(
                number2 = state.number2.dropLast(1)
            )
            state.operation != null -> state = state.copy(
                operation = null
            )
            state.number1.isNotBlank() -> state = state.copy(
                number1 = state.number1.dropLast(1)
            )
        }
    }
    private fun pi() {
        if (state.operation != null) {
            state = if (state.number2.contains("π")) {
                state.copy(number2 = state.number2.replace("π", ""))
            } else {
                state.copy(number2 = state.number2+ "π" )
            }
            return
        }
        state = if (state.number1.contains("π")){
            state.copy(number1 = state.number1.replace("π",""))
        }else{
            state.copy(number1 = state.number1+"π")
        }
    }
    private fun enterDecimal() {
        if(state.operation == null && !state.number1.contains(".") ) {
            if ( state.number1.isNotBlank()){
                state = state.copy(
                    number1 = state.number1 + "."
                )
                return
            }else{
                state = state.copy(
                    number1 = state.number1 + "0."
                )
                return
            }
        } else if(!state.number2.contains(".")) {
            if ( state.number2.isNotBlank()){
                state = state.copy(
                    number2 = state.number2 + "."
                )
                return
            }else{
                state = state.copy(
                    number2 = state.number2 + "0."
                )
                return
            }
        }
    }
    private fun percentage() {
        when {
            state.number2.isNotBlank() ->
                state = if (state.number2.contains("%")) {
                    state.copy(number2 = state.number2.replace("%", ""))
                } else {
                    state.copy(number2 = state.number2+ "%" )
                }

            state.operation != null ->
                return

            state.number1.isNotBlank() ->
                state = if (state.number1.contains("%")){
                    state.copy(number1 = state.number1.replace("%",""))
                }else{
                    state.copy(number1 = state.number1+"%")
                }
        }
    }
    private fun enterNumber(number: Int) {
        if(state.operation == null) {
            if(state.number1.length >= MAX_NUM_LENGTH
                || state.number2.contains("%")
                || state.number2.contains("π")) {
                return
            }
            if(number==10 &&  state.number1.isNotBlank()) {
                state = state.copy(number1 = state.number1 + "0")
                return
            }
            if(number==100 &&  state.number1.isNotBlank()) {
                state = state.copy(number1 = state.number1 + "00")
                return
            }
            state = state.copy(
                number1 = state.number1 + number
            )
            return
        }
        if(state.number2.length >= MAX_NUM_LENGTH
            || state.number2.contains("%")
            || state.number2.contains("π")){
            return
        }
        if(number==10 && state.number2.isNotBlank()) {
            state = state.copy(number1 = state.number2 + "0")
            return
        }
        if(number==100 && state.number2.isNotBlank()) {
            state = state.copy(number1 = state.number2 + "00")
            return
        }
        state = state.copy(
            number2 = state.number2 + number
        )
    }

    companion object {
        private const val MAX_NUM_LENGTH = 8
    }
    private fun stringToDouble(string: String):Double?{
        var tempDouble= string.toDoubleOrNull()
        when{
            string.contains("%")->
                tempDouble=((string.replace("%","").toDoubleOrNull()?: 0.0)/100)
            string.contains("π")->{

                tempDouble= ((string.replace("π","").toDoubleOrNull()?: 1.0)*3.14159265359)
            }
            string.contains("ans")->
                tempDouble= ((string.replace("ans","").toDoubleOrNull()?: 1.0)*lastAns!!)
            string=="-"->
                tempDouble=(-1.0)
        }
        return tempDouble
    }
}
