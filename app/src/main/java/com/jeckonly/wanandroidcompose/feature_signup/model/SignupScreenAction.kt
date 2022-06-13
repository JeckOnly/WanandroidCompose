package com.jeckonly.wanandroidcompose.feature_signup.model


/**
 * signup screen界面的一次性行为
 */
sealed class SignupScreenAction{
    object GoSignInScreenSuccess: SignupScreenAction()
    object GoHomeScreenSuccess: SignupScreenAction()
    class ShowErrorSnackBar(val message: String): SignupScreenAction()
}
