package com.jeckonly.wanandroidcompose.feature_signin

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jeckonly.core.ui.M
import com.jeckonly.core.util.LogUtil
import com.jeckonly.core.util.R
import com.jeckonly.wanandroidcompose.destinations.HomeScreenDestination
import com.jeckonly.wanandroidcompose.destinations.SigninScreenDestination
import com.jeckonly.wanandroidcompose.destinations.SignupScreenDestination
import com.jeckonly.wanandroidcompose.destinations.SplashScreenDestination
import com.jeckonly.wanandroidcompose.feature_home.HomeScreen
import com.jeckonly.wanandroidcompose.feature_signin.event.SigninEvent
import com.jeckonly.wanandroidcompose.feature_signin.model.SigninScreenAction
import com.jeckonly.wanandroidcompose.feature_signin.model.SigninScreenState
import com.jeckonly.wanandroidcompose.feature_signin.model.SigninUserEnterInfo
import com.jeckonly.wanandroidcompose.feature_signup.SignupViewModel
import com.jeckonly.wanandroidcompose.feature_signup.event.SignupEvent
import com.jeckonly.wanandroidcompose.feature_signup.model.SignupScreenAction
import com.jeckonly.wanandroidcompose.feature_signup.model.SignupScreenState
import com.jeckonly.wanandroidcompose.feature_signup.model.SignupUserEnterInfo
import com.jeckonly.wanandroidcompose.ui.theme.Blue5
import com.jeckonly.wanandroidcompose.ui.theme.HintColor
import com.jeckonly.wanandroidcompose.ui.theme.TextColor
import com.jeckonly.wanandroidcompose.ui.theme.Transparent
import com.jeckonly.wanandroidcompose.ui.widget.BasicTextFieldWithPlaceHolder
import com.jeckonly.wanandroidcompose.ui.widget.BlueSnackBarWithMessage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@Destination
@Composable
fun SigninScreen(
    navigator: DestinationsNavigator,
    viewModel: SigninViewModel = hiltViewModel()
) {
    // Remember a SystemUiController
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight
    val context = LocalContext.current

    val userNameTextValue = remember { mutableStateOf("") }
    val passwordTextValue = remember { mutableStateOf("") }
    val radioButtonState = remember { mutableStateOf(false) }

    val signinScreenState = viewModel.signinScreenState.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Transparent,
            darkIcons = useDarkIcons
        )
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.signinScreenAction.collect { action ->
            when(action) {
                SigninScreenAction.GoHomeScreenSuccess -> {
                    snackbarHostState.showSnackbar("Signin successfully")
                    navigator.navigate(direction = HomeScreenDestination, builder = {
                        popUpTo(SigninScreenDestination.route) {
                            inclusive = true
                        }
                    })
                    LogUtil.d("前往 home")
                }
                is SigninScreenAction.ShowErrorSnackBar -> {
                    snackbarHostState.showSnackbar(action.message)
                }
            }
        }
    }


    ConstraintLayout(modifier = M.fillMaxSize()) {
        val (imageBackground, signUpArea, snackBar) = createRefs()
        Image(
            painter = painterResource(id = R.drawable.background_login),
            contentDescription = null,
            modifier = M
                .constrainAs(imageBackground) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .clickable {
                    scope.launch {

                        snackbarHostState.showSnackbar("dfdfsf")
                    }
                },
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )

        SigninArea(
            viewModel,
            navigator,
            signinScreenState,
            userNameTextValue,
            passwordTextValue,
            radioButtonState,
            modifier = M.constrainAs(signUpArea) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            }
        )

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = M.constrainAs(snackBar) {
                bottom.linkTo(parent.bottom, 15.dp)
            }
        ) {
            BlueSnackBarWithMessage(message = it.message)
        }
    }
}

@Composable
fun SigninArea(
    viewModel: SigninViewModel,
    navigator: DestinationsNavigator,
    signinScreenState: State<SigninScreenState>,
    userNameTextValue: MutableState<String>,
    passwordTextValue: MutableState<String>,
    radioButtonState: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {

    // 当处于Loading状态时，开启旋转动画
    val rotateDegree = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = signinScreenState.value.isLoading) {
        if (signinScreenState.value.isLoading) {
            rotateDegree.animateTo(360f, infiniteRepeatable(
                TweenSpec(300)
            ))
        }else {
            rotateDegree.animateTo(0f)
        }
    }

    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .background(
                MaterialTheme.colors.surface
            )
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        ConstraintLayout {
            val (titleText, userText, passwordText, radioButton, rememberMeText, forgotText, signinText, signupText, commitButton) = createRefs()

            Text(
                text = "Welcome back",
                modifier = M.constrainAs(titleText) {
                    top.linkTo(parent.top, 50.dp)
                    start.linkTo(parent.start, 38.dp)
                },
                color = Blue5,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            BasicTextFieldWithPlaceHolder(
                textValue = userNameTextValue,
                onValueChange = {
                    if (it.length <= 20)
                        userNameTextValue.value = it
                },
                placeHolder = "Name",
                textSize = 16.sp,
                textColor = TextColor,
                placeHolderTextColor = HintColor,
                isPassword = false,
                modifier = M.constrainAs(userText) {
                    top.linkTo(titleText.bottom, 50.dp)
                    start.linkTo(parent.start, 38.dp)
                    end.linkTo(parent.end, 38.dp)
                    width = Dimension.fillToConstraints
                }

            )

            BasicTextFieldWithPlaceHolder(
                textValue = passwordTextValue,
                onValueChange = {
                    if (it.length <= 20)
                        passwordTextValue.value = it
                },
                placeHolder = "Password",
                textSize = 16.sp,
                textColor = TextColor,
                placeHolderTextColor = HintColor,
                isPassword = true,
                modifier = M.constrainAs(passwordText) {
                    top.linkTo(userText.bottom, 30.dp)
                    start.linkTo(userText.start)
                    end.linkTo(userText.end)
                    width = Dimension.fillToConstraints
                }
            )

            RadioButton(
                selected = radioButtonState.value,
                onClick = {
                    radioButtonState.value = !radioButtonState.value
                },
                colors = RadioButtonDefaults.colors(Blue5, Blue5, Blue5),
                modifier = M.constrainAs(radioButton) {
                    top.linkTo(passwordText.bottom, 25.dp)
                    start.linkTo(passwordText.start, (-12).dp)
                })

            Text(
                "Remember me",
                modifier = M.constrainAs(rememberMeText) {
                    top.linkTo(radioButton.top)
                    bottom.linkTo(radioButton.bottom)
                    start.linkTo(radioButton.end)
                    width = Dimension.wrapContent
                },
                color = TextColor,
                fontSize = 12.sp
            )

            Text(
                "Forgot password?",
                modifier = M.constrainAs(forgotText) {
                    top.linkTo(radioButton.top)
                    bottom.linkTo(radioButton.bottom)
                    end.linkTo(passwordText.end)
                    width = Dimension.wrapContent
                },
                color = Blue5,
                fontSize = 12.sp,
                style = TextStyle(textDecoration = TextDecoration.Underline)
            )

            Text(
                text = "Sign In",
                modifier = M.constrainAs(signinText) {
                    top.linkTo(radioButton.bottom, 30.dp)
                    start.linkTo(passwordText.start)
                },
                color = Blue5,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Sing Up",
                modifier = M
                    .constrainAs(signupText) {
                        top.linkTo(signinText.bottom, 20.dp)
                        bottom.linkTo(parent.bottom, 30.dp)
                        start.linkTo(signinText.start)
                    }
                    .clickable {
                        // 前往signup
                        navigator.navigate(direction = SignupScreenDestination, builder = {
                            launchSingleTop = true
                        })
                    },
                color = Blue5,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(textDecoration = TextDecoration.Underline)
            )

            IconButton(onClick = {
                viewModel.onEvent(
                    SigninEvent.ClickCommit(
                        SigninUserEnterInfo(
                            username = userNameTextValue.value,
                            password = passwordTextValue.value,
                            rememberMe = radioButtonState.value
                        )
                    )
                )
            }, modifier = M
                .constrainAs(commitButton) {
                    top.linkTo(signinText.top)
                    bottom.linkTo(signinText.bottom)
                    end.linkTo(passwordText.end)
                }
                .background(Blue5, CircleShape)
                .padding(5.dp)
            )
            {
                Icon(
                    painter = painterResource(id = R.drawable.icon_right_arrow),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = M
                        .size(15.dp)
                        .rotate(rotateDegree.value)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignUpArea() {
//    val userNameTextValue = remember { mutableStateOf("") }
//    val passwordTextValue = remember { mutableStateOf("") }
//    val repasswordTextValue = remember { mutableStateOf("") }
//    val radioButtonState = remember { mutableStateOf(false) }
//
//    val loginViewModel: LoginViewModel by viewModel()
////    Surface(color = Color.Black) {
////        SignUpArea(
////            loginViewModel,
////            userNameTextValue,
////            passwordTextValue,
////            repasswordTextValue,
////            radioButtonState,
////        )
////    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSnackBar() {
    Surface(modifier = M
        .fillMaxWidth()
        .wrapContentHeight(), color = Blue5.copy(alpha = 0.7f)) {
        Text(
            text = "it.message",
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 7.dp, top = 6.dp, bottom = 6.dp)
        )
    }
}