package com.jeckonly.wanandroidcompose.data.repository.fake

import com.jeckonly.wanandroidcompose.data.mapper.toRegisterSuccessInfo
import com.jeckonly.wanandroidcompose.data.remote.RemoteConstant
import com.jeckonly.wanandroidcompose.data.remote.WACApi
import com.jeckonly.wanandroidcompose.data.remote.dto.common.RemoteDto
import com.jeckonly.wanandroidcompose.data.remote.dto.login.LoginDto
import com.jeckonly.wanandroidcompose.data.util.ResourceState
import com.jeckonly.wanandroidcompose.domain.model.login.RegisterSuccessInfo
import com.jeckonly.wanandroidcompose.domain.repository.LoginRepository
import com.jeckonly.wanandroidcompose.feature_login.model.UserEnterInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


@Singleton
class RemoteLoginRepository @Inject constructor(
    @Named("WACApi") val api: WACApi
) : LoginRepository {
    override suspend fun signUp(userEnterInfo: UserEnterInfo): Flow<ResourceState<RegisterSuccessInfo>> {
        return flow {
            emit(ResourceState.Loading(true))
            delay(1000L)

            try {
                val remoteDto = api.register(
                    userEnterInfo.username,
                    userEnterInfo.password,
                    userEnterInfo.repassword
                )
                emit(ResourceState.Loading(false))
                if (remoteDto.errorCode != RemoteConstant.SUCCESS_CODE || remoteDto.data == null) {
                    emit(ResourceState.Error(message = remoteDto.errorMsg))
                }else {
                    // 成功的情况
                    emit(ResourceState.Success(data = remoteDto.data.toRegisterSuccessInfo()))
                }
            } catch (e: Exception) {
                emit(ResourceState.Loading(false))
                emit(ResourceState.Error(message = "Please check your Internet"))
            }
        }
    }
}