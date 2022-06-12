package com.jeckonly.wanandroidcompose.feature_login.model


/**
 * login screen界面的状态
 */
data class LoginScreenState(
    val isLoading: Boolean = false,
    val repasswordNotSameError: Boolean = false,
)
