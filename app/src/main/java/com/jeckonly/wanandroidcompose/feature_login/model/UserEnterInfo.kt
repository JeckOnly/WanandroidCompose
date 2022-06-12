package com.jeckonly.wanandroidcompose.feature_login.model

data class UserEnterInfo(
    val username: String,
    val password: String,
    val repassword: String,
    val agreePolicy: Boolean
)
