package com.abdalllahyascer.HistoryCalc

sealed class CalculatorAction {
    data class Number(val number: Int): CalculatorAction()
    data class Operation(val operation: CalculatorOperation): CalculatorAction()
    object Clear: CalculatorAction()
    object PI: CalculatorAction()
    object Percentage: CalculatorAction()
    object LastAns: CalculatorAction()
    object Negative: CalculatorAction()
    object Delete: CalculatorAction()
    object Calculate: CalculatorAction()
    object Decimal: CalculatorAction()
}