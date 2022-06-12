package com.jeckonly.wanandroidcompose.domain.repository

import com.jeckonly.wanandroidcompose.data.remote.dto.common.RemoteDto
import com.jeckonly.wanandroidcompose.data.remote.dto.login.LoginDto
import com.jeckonly.wanandroidcompose.data.util.ResourceState
import com.jeckonly.wanandroidcompose.domain.model.login.RegisterSuccessInfo
import com.jeckonly.wanandroidcompose.feature_login.model.UserEnterInfo
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun signUp(userEnterInfo: UserEnterInfo): Flow<ResourceState<RegisterSuccessInfo>>
}