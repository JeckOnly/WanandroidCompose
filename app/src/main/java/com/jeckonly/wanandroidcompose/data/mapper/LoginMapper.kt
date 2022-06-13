package com.jeckonly.wanandroidcompose.data.mapper

import com.jeckonly.wanandroidcompose.data.remote.dto.login.SigninDto
import com.jeckonly.wanandroidcompose.data.remote.dto.login.SignupDto
import com.jeckonly.wanandroidcompose.domain.model.signin.SigninSuccessInfo
import com.jeckonly.wanandroidcompose.domain.model.signup.SignupSuccessInfo
import com.jeckonly.wanandroidcompose.feature_profile.model.UserInfo

fun SignupDto.toSignupSuccessInfo(): SignupSuccessInfo {
    return SignupSuccessInfo(
        username = this.username,
        id = this.id,
        coinCount = coinCount
    )
}


fun SigninDto.toSigninSuccessInfo(): SigninSuccessInfo {
    return SigninSuccessInfo(
        username = this.username,
        id = this.id,
        coinCount = coinCount
    )
}
