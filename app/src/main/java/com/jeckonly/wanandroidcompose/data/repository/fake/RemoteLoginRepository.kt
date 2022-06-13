package com.jeckonly.wanandroidcompose.data.repository.fake

import com.jeckonly.wanandroidcompose.data.mapper.toSigninSuccessInfo
import com.jeckonly.wanandroidcompose.data.mapper.toSignupSuccessInfo
import com.jeckonly.wanandroidcompose.data.remote.RemoteConstant
import com.jeckonly.wanandroidcompose.data.remote.WACApi
import com.jeckonly.wanandroidcompose.data.util.ResourceState
import com.jeckonly.wanandroidcompose.domain.model.signin.SigninSuccessInfo
import com.jeckonly.wanandroidcompose.domain.model.signup.SignupSuccessInfo
import com.jeckonly.wanandroidcompose.domain.repository.LoginRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


@Singleton
class RemoteLoginRepository @Inject constructor(
    @Named("WACApi") val api: WACApi
) : LoginRepository {
    override suspend fun signUp(username: String, password: String, repassword: String): Flow<ResourceState<SignupSuccessInfo>> {
        return flow {
            emit(ResourceState.Loading(true))
            delay(1000L)

            try {
                val remoteDto = api.signUp(
                    username,
                    password,
                    repassword
                )
                emit(ResourceState.Loading(false))
                if (remoteDto.errorCode != RemoteConstant.SUCCESS_CODE || remoteDto.data == null) {
                    emit(ResourceState.Error(message = remoteDto.errorMsg))
                }else {
                    // 成功的情况
                    emit(ResourceState.Success(data = remoteDto.data.toSignupSuccessInfo()))
                }
            } catch (e: Exception) {
                emit(ResourceState.Loading(false))
                emit(ResourceState.Error(message = "Please check your Internet"))
            }
        }
    }

    override suspend fun signIn(
        username: String,
        password: String
    ): Flow<ResourceState<SigninSuccessInfo>> {
        return flow {
            emit(ResourceState.Loading(true))
            delay(1000L)

            try {
                val remoteDto = api.signIn(
                    username,
                    password
                )
                emit(ResourceState.Loading(false))
                if (remoteDto.errorCode != RemoteConstant.SUCCESS_CODE || remoteDto.data == null) {
                    emit(ResourceState.Error(message = remoteDto.errorMsg))
                }else {
                    // 成功的情况
                    emit(ResourceState.Success(data = remoteDto.data.toSigninSuccessInfo()))
                }
            } catch (e: Exception) {
                emit(ResourceState.Loading(false))
                emit(ResourceState.Error(message = "Please check your Internet"))
            }
        }
    }
}