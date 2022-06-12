package com.jeckonly.wanandroidcompose.data.mapper

import com.jeckonly.wanandroidcompose.data.remote.dto.login.RegisterDto
import com.jeckonly.wanandroidcompose.domain.model.login.RegisterSuccessInfo

fun RegisterDto.toRegisterSuccessInfo(): RegisterSuccessInfo {
    return RegisterSuccessInfo(
        username = this.username,
        id = this.id
    )
}