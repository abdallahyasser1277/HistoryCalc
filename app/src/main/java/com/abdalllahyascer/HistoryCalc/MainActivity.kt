
package com.abdalllahyascer.HistoryCalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abdalllahyascer.HistoryCalc.Values.Companion.buttonSpacing
import com.abdalllahyascer.HistoryCalc.repo.Calculation
import com.abdalllahyascer.HistoryCalc.ui.theme.*
import com.abdalllahyascer.HistoryCalc.ui.theme.LightGray
import com.abdalllahyascer.HistoryCalc.ui.theme.MediumGray
import com.abdalllahyascer.HistoryCalc.ui.theme.Orange
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            CalculatorTheme {

                val vm = viewModel<CalculatorViewModel>()

                val sheetState = rememberBottomSheetState(
                    initialValue = BottomSheetValue.Collapsed
                )
                val scaffoldState= rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
                val scope = rememberCoroutineScope()
                BottomSheetScaffold(
                    scaffoldState = scaffoldState,
                    sheetContent = {
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.03f)
                            .background(Color.Black)
                            .padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(buttonSpacing)

                            ){Card (
                                modifier = Modifier.fillMaxWidth()
                            )
                            {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(MediumGray)
                                ){
                                    Text(text = vm.notedCalculationState.calculationText+vm.notedCalculationState.result.toString(),
                                        fontSize = 36.sp,
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 8.dp)
                                    )
                                    Text(text = vm.notedCalculationState.date?:"",
                                        color = Orange,
                                        fontSize = 20.sp,
                                        modifier = Modifier.padding(horizontal = 8.dp)
                                    )
                                }
                            }
                            BasicTextField(
                                value = vm.noteState,
                                onValueChange = { newText ->
                                    vm.noteState = newText
                                },
                                textStyle = TextStyle(
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 24.sp,
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .clip(
                                        RoundedCornerShape(8.dp)
                                    )
                                    .background(LightGray)
                                    .padding(8.dp),
                                decorationBox = {inner->
                                    if (vm.noteState.text==""){
                                        Text(text = "add note here",
                                            fontSize = 24.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color.White
                                        )
                                    }
                                    inner()
                                }
                            )
                            Row (horizontalArrangement = Arrangement.spacedBy(buttonSpacing)){
                                CalculatorButton(
                                    symbol = "Save",
                                    color = Orange,
                                    textSize = 28.sp,
                                    modifier = Modifier
                                        .aspectRatio(4.5f)
                                        .weight(9f)
                                )
                                {
                                    vm.updateCalculation()
                                    scope.launch {
                                        if (sheetState.isCollapsed)
                                            sheetState.expand()
                                        else
                                            sheetState.collapse()
                                    }

                                }
                                CalculatorButton(
                                        symbol = "Del",
                                color = LightGray,
                                textSize = 28.sp,
                                modifier = Modifier
                                    .aspectRatio(1.5f)
                                    .weight(3f)
                                ){
                                vm.deleteCalculation()
                                scope.launch {
                                    if (sheetState.isCollapsed)
                                        sheetState.expand()
                                    else
                                        sheetState.collapse()
                                }
                                }
                                CalculatorButton(
                                    symbol = "Clr",
                                    color = LightGray,
                                    textSize = 28.sp,
                                    modifier = Modifier
                                        .aspectRatio(1.5f)
                                        .weight(3f)
                                ){
                                    vm.clearNote()
                                }

                            }


                        }
                    }, sheetPeekHeight = 0.dp
                ) {
                    CalculatorScreen( vm,sheetState,scope)
                }

            }
        }
    }

    @Composable
    private fun CalculatorScreen(
        vm: CalculatorViewModel,sheetState: BottomSheetState,scope: CoroutineScope
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                verticalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                ){
                    LazyColumn(reverseLayout = true,) {
                         items(vm.calculationList.asReversed()) { calculation->
                             ResultCard(calculation,vm,sheetState,scope)
                         }
                     }
                }
                Text(
                    text = vm.state.number1 + (vm.state.operation?.symbol ?: "") + vm.state.number2,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth(),
                    fontWeight = FontWeight.Light,
                    fontSize = 60.sp,
                    color = Color.White,
                    maxLines = 2,
                    lineHeight = 50.sp
                )
                KeyPad(vm)
            }
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    private fun ResultCard(calculation:Calculation,vm: CalculatorViewModel,sheetState: BottomSheetState,scope: CoroutineScope) {
        Card(
            modifier = Modifier
                .padding(top = 4.dp, bottom = 4.dp)
                .fillMaxWidth()
        )
        {
            Row(modifier = Modifier
                .background(MediumGray)
                .padding(8.dp)) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = calculation.calculationText+" "+calculation.result,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 12.dp),
                        fontSize = 24.sp
                    )
                    calculation.date?.let {
                        Text(
                            text = it,
                            color = Orange,
                            modifier = Modifier.padding(horizontal = 12.dp),
                        )
                    }
                }
                val text =if (calculation.note=="") "Add Note" else "Noted"
                val color =if (calculation.note=="") LightGray  else Orange
                CalculatorButton(
                    symbol = text,
                    color = color,
                    textSize = 20.sp,
                    modifier = Modifier.padding(8.dp)
                ){
                    scope.launch {
                        vm.updateNote(calculation)
                        if (sheetState.isCollapsed)
                            sheetState.expand()
                        else
                            sheetState.collapse()
                    }
                }
                }
            }
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    private fun KeyPad(viewModel: CalculatorViewModel) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                verticalArrangement = Arrangement.spacedBy(buttonSpacing),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton(
                        symbol = "AC",
                        color = LightGray,
                        textSize = 28.sp,
                        modifier = Modifier
                            .aspectRatio(3f)
                            .weight(3f)
                    ) {
                        viewModel.onAction(CalculatorAction.Clear)
                    }
                    CalculatorButton(
                        symbol = "Del",
                        color = LightGray,
                        textSize = 28.sp,
                        modifier = Modifier
                            .aspectRatio(1.5f)
                            .weight(1.5f)
                    ) {
                        viewModel.onAction(CalculatorAction.Delete)
                    }
                    CalculatorButton(
                        symbol = "Ans",
                        textSize = 28.sp,
                        color = LightGray,
                        modifier = Modifier
                            .aspectRatio(1.5f)
                            .weight(1.5f)
                    ) {
                        viewModel.onAction(CalculatorAction.LastAns)
                    }

                    CalculatorButton(
                        symbol = "Neg",
                        textSize = 28.sp,
                        color = LightGray,
                        modifier = Modifier
                            .aspectRatio(1.5f)
                            .weight(1.5f)
                    ) {
                        viewModel.onAction(CalculatorAction.Negative)
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton(
                        symbol = "7",
                        color = MediumGray,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onAction(CalculatorAction.Number(7))
                    }
                    CalculatorButton(
                        symbol = "8",
                        color = MediumGray,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onAction(CalculatorAction.Number(8))
                    }
                    CalculatorButton(
                        symbol = "9",
                        color = MediumGray,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onAction(CalculatorAction.Number(9))
                    }
                    CalculatorButton(
                        symbol = "%",
                        color = Orange,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onAction(CalculatorAction.Percentage)
                    }
                    CalculatorButton(
                        symbol = "π",
                        color = Orange,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onAction(CalculatorAction.PI)
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton(
                        symbol = "4",
                        color = MediumGray,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onAction(CalculatorAction.Number(4))
                    }
                    CalculatorButton(
                        symbol = "5",
                        color = MediumGray,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onAction(CalculatorAction.Number(5))
                    }
                    CalculatorButton(
                        symbol = "6",
                        color = MediumGray,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onAction(CalculatorAction.Number(6))
                    }
                    CalculatorButton(
                        symbol = "−",
                        color = Orange,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Subtract))
                    }
                    CalculatorButton(
                        symbol = "÷",
                        color = Orange,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Divide))
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton(
                        symbol = "1",
                        color = MediumGray,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onAction(CalculatorAction.Number(1))
                    }
                    CalculatorButton(
                        symbol = "2",
                        color = MediumGray,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onAction(CalculatorAction.Number(2))
                    }
                    CalculatorButton(
                        symbol = "3",
                        color = MediumGray,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onAction(CalculatorAction.Number(3))
                    }
                    CalculatorButton(
                        symbol = "+",
                        color = Orange,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Add))
                    }
                    CalculatorButton(
                        symbol = "×",
                        color = Orange,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Multiply))
                    }

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton(
                        symbol = "0",
                        color = MediumGray,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onAction(CalculatorAction.Number(10))
                    }
                    CalculatorButton(
                        symbol = "00",
                        color = MediumGray,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onAction(CalculatorAction.Number(100))
                    }
                    CalculatorButton(
                        symbol = ".",
                        color = MediumGray,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onAction(CalculatorAction.Decimal)
                    }
                    CalculatorButton(
                        symbol = "=",
                        color = Orange,
                        modifier = Modifier
                            .aspectRatio(2f)
                            .weight(2f)
                    ) {
                        viewModel.onAction(CalculatorAction.Calculate)
                    }
                }
            }
        }
    }


