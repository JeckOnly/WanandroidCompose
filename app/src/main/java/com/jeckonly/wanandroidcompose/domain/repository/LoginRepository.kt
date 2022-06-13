package com.jeckonly.wanandroidcompose.domain.repository

import com.jeckonly.wanandroidcompose.data.util.ResourceState
import com.jeckonly.wanandroidcompose.domain.model.signin.SigninSuccessInfo
import com.jeckonly.wanandroidcompose.domain.model.signup.SignupSuccessInfo
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun signUp(username: String, password: String, repassword: String): Flow<ResourceState<SignupSuccessInfo>>

    suspend fun signIn(username: String, password: String): Flow<ResourceState<SigninSuccessInfo>>
}