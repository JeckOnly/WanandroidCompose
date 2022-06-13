package com.jeckonly.wanandroidcompose.feature_signup.event

import com.jeckonly.wanandroidcompose.feature_signup.model.SignupUserEnterInfo

sealed class SignupEvent {
    data class ClickCommit(
        val signupUserEnterInfo: SignupUserEnterInfo
    ) : SignupEvent()

    object ChangeRepassword: SignupEvent()
}
