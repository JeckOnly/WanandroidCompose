package com.jeckonly.wanandroidcompose.feature_login.event

import com.jeckonly.wanandroidcompose.feature_login.model.UserEnterInfo
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

sealed class LoginEvent {
    data class ClickCommit(
        val userEnterInfo: UserEnterInfo
    ) : LoginEvent()

    object ChangeRepassword: LoginEvent()
}
