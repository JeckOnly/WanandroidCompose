package com.jeckonly.wanandroidcompose.feature_signup.model

data class SignupUserEnterInfo(
    val username: String,
    val password: String,
    val repassword: String,
    val agreePolicy: Boolean
)
