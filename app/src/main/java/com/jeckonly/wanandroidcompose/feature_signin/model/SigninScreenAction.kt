package com.jeckonly.wanandroidcompose.feature_signin.model


/**
 * sign in screen界面的一次性行为
 */
sealed class SigninScreenAction{
    object GoHomeScreenSuccess: SigninScreenAction()
    class ShowErrorSnackBar(val message: String): SigninScreenAction()
}
