package com.jeckonly.wanandroidcompose.data.mapper

import com.jeckonly.wanandroidcompose.data.remote.dto.login.SigninDto
import com.jeckonly.wanandroidcompose.data.remote.dto.login.SignupDto
import com.jeckonly.wanandroidcompose.domain.model.signin.SigninSuccessInfo
import com.jeckonly.wanandroidcompose.domain.model.signup.SignupSuccessInfo

fun SignupDto.toSignupSuccessInfo(): SignupSuccessInfo {
    return SignupSuccessInfo(
        username = this.username,
        id = this.id
    )
}


fun SigninDto.toSigninSuccessInfo(): SigninSuccessInfo {
    return SigninSuccessInfo(
        username = this.username,
        id = this.id
    )
}