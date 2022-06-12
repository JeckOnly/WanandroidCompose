package com.jeckonly.wanandroidcompose.ui.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jeckonly.core.ui.M
import com.jeckonly.wanandroidcompose.ui.theme.Blue5

@Composable
fun BlueSnackBarWithMessage(message:String) {
    Surface(modifier = M.fillMaxWidth().wrapContentHeight(), color = Blue5.copy(alpha = 0.7f)) {
        Text(
            text = message,
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 7.dp, top = 6.dp, bottom = 6.dp)
        )
    }
}