package com.jeckonly.wanandroidcompose.data.remote.dto.common

data class RemoteDto <T>(
    val data: T?,
    val errorCode: Int,
    val errorMsg: String
)