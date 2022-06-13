package com.jeckonly.wanandroidcompose.feature_splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavOptions
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jeckonly.core.ui.M
import com.jeckonly.core.util.R
import com.jeckonly.core.util.SPConstant
import com.jeckonly.core.util.SPUtil
import com.jeckonly.wanandroidcompose.WACApplication
import com.jeckonly.wanandroidcompose.destinations.HomeScreenDestination
import com.jeckonly.wanandroidcompose.destinations.SigninScreenDestination
import com.jeckonly.wanandroidcompose.destinations.SignupScreenDestination
import com.jeckonly.wanandroidcompose.destinations.SplashScreenDestination
import com.jeckonly.wanandroidcompose.ui.theme.Transparent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import kotlin.math.log

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
        delay(1000L)
        // 导航到其他界面并弹出splash
        val direction = if (
            SPUtil.get(WACApplication.getApplication(), SPConstant.HAD_SIGNIN, false) as Boolean
        ) {
            HomeScreenDestination
        } else {
            SigninScreenDestination
        }
        navigator.navigate(direction = direction, builder = {
            popUpTo(SplashScreenDestination.route) {
                inclusive = true
            }
        })
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

    ConstraintLayout(M.fillMaxSize().background(Color.White)) {
        val (logo) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.icon_google),
            contentDescription = null,
            modifier = M.constrainAs(logo) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                linkTo(top = parent.top, bottom = parent.bottom, bias = 0.382f)
            }.size(100.dp)
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewBox() {
    IconAndText()
}