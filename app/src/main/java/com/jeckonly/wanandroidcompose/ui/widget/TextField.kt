package com.jeckonly.wanandroidcompose.ui.widget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jeckonly.core.ui.M

@Composable
fun BasicTextFieldWithPlaceHolder(
    textValue: MutableState<String>,
    onValueChange: (String) -> Unit,
    placeHolder: String,
    textSize: TextUnit,
    textColor: Color,
    placeHolderTextColor: Color,
    isPassword: Boolean,
    modifier: Modifier = Modifier
) {
    val textStyle = remember {
        TextStyle(
            color = textColor,
            fontSize = textSize,
        )
    }
    BasicTextField(
        value = textValue.value,
        onValueChange = {
            onValueChange(it)
        },
        modifier = modifier,
        textStyle = textStyle,
        keyboardOptions = if (isPassword) {
            KeyboardOptions(keyboardType = KeyboardType.Password)
        } else {
            KeyboardOptions(keyboardType = KeyboardType.Text)
        },
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    ) { innerTextField ->
        Column {
            Box() {
                androidx.compose.animation.AnimatedVisibility(
                    visible = textValue.value.isEmpty(),
                    enter = fadeIn(), exit = fadeOut()
                ) {
                    Text(text = placeHolder, color = placeHolderTextColor, fontSize = textSize)
                }
                innerTextField()
            }
            Divider(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp))
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewBasicTextFieldWithPlaceHolder() {
    val userNameTextValue = remember { mutableStateOf("JeckOnly") }


}