package com.jeckonly.wanandroidcompose.feature_signin.event

import com.jeckonly.wanandroidcompose.feature_signin.model.SigninUserEnterInfo

sealed class SigninEvent {
    data class ClickCommit(
        val signinUserEnterInfo: SigninUserEnterInfo
    ) : SigninEvent()
}
