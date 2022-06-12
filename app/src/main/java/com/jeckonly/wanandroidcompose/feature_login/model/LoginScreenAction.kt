package com.jeckonly.wanandroidcompose.feature_login.model


/**
 * login screen界面的一次性行为
 */
sealed class LoginScreenAction{
    object GoSignInScreenSuccess: LoginScreenAction()
    object GoHomeScreenSuccess: LoginScreenAction()
    class ShowErrorSnackBar(val message: String): LoginScreenAction()
}
