package com.abdalllahyascer.HistoryCalc

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculatorViewModel :ViewModel() {
    var state by mutableStateOf(CalculatorState())

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

    private fun percentage() {
        var result:Double
        when {
        state.number2.isNotBlank() ->{
            result= state.number2.toDoubleOrNull()!!/100
            state = state.copy(number2 = result.toString())}

        state.operation != null ->
            return

        state.number1.isNotBlank() ->{
            result= state.number1.toDoubleOrNull()!!/100
            state = state.copy(number1 = result.toString())}
    }
    }

    private fun pi() {
        var result:Double
        when {
            state.number2.isNotBlank() ->{
                result= state.number2.toDoubleOrNull()!!*3.1415926536
                state = state.copy(number2 = result.toString())}

            state.operation != null ->
                state = state.copy(
                    number2="3.1415926536"
                )
            state.number1.isNotBlank() ->{
                result= state.number1.toDoubleOrNull()!!*3.1415926536
                state = state.copy(number1 = result.toString())}
        }
    }

    private fun negative() {
        when {

            state.number2.isNotBlank() ->
                state = if (state.number2.contains("-")){
                            state.copy(number2 = state.number2.replace("-",""))
                        }else{
                            state.copy(number2 = "-"+state.number2)
                        }

            state.operation != null ->
                return

            state.number1.isNotBlank() ->
                state = if (state.number1.contains("-")){
                            state.copy(number1 = state.number1.replace("-",""))
                        }else{
                            state.copy(number1 = "-"+state.number1)
                        }

        }
    }

    private fun lastAns() {
        TODO("Not yet implemented")
    }

    private fun enterOperation(operation: CalculatorOperation) {
        if(state.number1.isNotBlank()) {
            state = state.copy(operation = operation)
        }
    }

    private fun calculate() {
        val number1 = state.number1.toDoubleOrNull()
        val number2 = state.number2.toDoubleOrNull()
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

    private fun enterNumber(number: Int) {
        if(state.operation == null) {
            if(state.number1.length >= MAX_NUM_LENGTH) {
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
        if(state.number2.length >= MAX_NUM_LENGTH ){
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
}
