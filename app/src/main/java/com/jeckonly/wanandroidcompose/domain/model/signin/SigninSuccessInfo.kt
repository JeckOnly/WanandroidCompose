package com.jeckonly.wanandroidcompose.domain.model.signin

import com.jeckonly.wanandroidcompose.feature_profile.model.UserInfo

data class SigninSuccessInfo(
    val username: String,
    val id: Int,
    val coinCount: Int = 0
) {
    fun toUserInfo() = UserInfo(
        username = username,
        id = id
    )
}
