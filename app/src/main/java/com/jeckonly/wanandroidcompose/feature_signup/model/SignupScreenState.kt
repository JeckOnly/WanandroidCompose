package com.jeckonly.wanandroidcompose.feature_signup.model


/**
 * login screen界面的状态
 */
data class SignupScreenState(
    val isLoading: Boolean = false,
    val repasswordNotSameError: Boolean = false,
)
