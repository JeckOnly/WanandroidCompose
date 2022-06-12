package com.jeckonly.wanandroidcompose.di

import com.jeckonly.wanandroidcompose.data.repository.fake.RemoteLoginRepository
import com.jeckonly.wanandroidcompose.domain.repository.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


    @Binds
    @Singleton
    abstract fun bindLoginRepo(
        loginRepository: RemoteLoginRepository
    ): LoginRepository
}