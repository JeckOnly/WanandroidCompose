package com.jeckonly.wanandroidcompose.feature_splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jeckonly.core.ui.M
import com.jeckonly.core.util.R
import com.jeckonly.wanandroidcompose.destinations.LoginScreenDestination
import com.jeckonly.wanandroidcompose.ui.theme.Transparent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay

@Destination(start = true)
@Composable
fun SplashScreen(
    navigator: DestinationsNavigator
) {
    // Remember a SystemUiController
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight

    // 延迟一定时间进行跳转
    LaunchedEffect(key1 = Unit, block = {
        delay(2000L)
        navigator.navigate(LoginScreenDestination)
    })
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Transparent,
            darkIcons = useDarkIcons
        )
    }

    IconAndText()
}

@Composable
fun IconAndText() {
    val context = LocalContext.current
    Box(
        modifier = M
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon_google),
            contentDescription = null,
            modifier = M.align(
                Alignment.Center
            )
        )

        Text(text = context.getString(R.string.byJeckOnly), modifier = M
            .align(Alignment.BottomCenter)
            .padding(bottom = 20.dp), color = MaterialTheme.colors.onBackground)
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewBox() {
    IconAndText()
}