package com.abdalllahyascer.HistoryCalc

import com.abdalllahyascer.HistoryCalc.repo.Calculation

class State(var calculatorState: CalculatorState =CalculatorState(),
            var calculationList: List<Calculation> = emptyList<Calculation>()

) {
}