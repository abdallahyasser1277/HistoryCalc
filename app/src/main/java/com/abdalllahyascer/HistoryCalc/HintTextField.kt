package com.abdalllahyascer.HistoryCalc

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HintTextField(
    value:String,
    onValueChange: (String)->Unit,
    hintText:String="",
    modifier:Modifier= Modifier,
    maxLines:Int=5
){
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        maxLines=maxLines,
        )
}