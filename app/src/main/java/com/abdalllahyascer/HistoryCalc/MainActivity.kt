package com.abdalllahyascer.HistoryCalc
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abdalllahyascer.HistoryCalc.Values.Companion.buttonSpacing
import com.abdalllahyascer.HistoryCalc.ui.theme.*
import com.abdalllahyascer.HistoryCalc.ui.theme.LightGray
import com.abdalllahyascer.HistoryCalc.ui.theme.MediumGray
import com.abdalllahyascer.HistoryCalc.ui.theme.Orange


@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            CalculatorTheme {

                val viewModel = viewModel<CalculatorViewModel>()
                val state = viewModel.state

                CalculatorScreen(state, viewModel)
            }
        }
    }

    @Composable
    private fun CalculatorScreen(
        state: CalculatorState,
        viewModel: CalculatorViewModel
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                verticalArrangement = Arrangement.spacedBy(buttonSpacing),
            ) {
                Text(
                    text = state.number1 + (state.operation?.symbol ?: "") + state.number2,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    fontWeight = FontWeight.Light,
                    fontSize = 60.sp,
                    color = Color.White,
                    maxLines = 2,
                    lineHeight = 50.sp
                )
                KeyPad(viewModel)
            }
        }
    }

    @Composable
    private fun KeyPad(viewModel: CalculatorViewModel) {
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

