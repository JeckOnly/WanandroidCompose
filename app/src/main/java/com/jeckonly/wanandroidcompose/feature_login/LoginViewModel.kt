package com.jeckonly.wanandroidcompose.feature_login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeckonly.wanandroidcompose.data.util.ResourceState
import com.jeckonly.wanandroidcompose.domain.repository.LoginRepository
import com.jeckonly.wanandroidcompose.feature_login.event.LoginEvent
import com.jeckonly.wanandroidcompose.feature_login.model.LoginScreenAction
import com.jeckonly.wanandroidcompose.feature_login.model.LoginScreenState
import com.jeckonly.wanandroidcompose.feature_splash.event.SplashEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: LoginRepository
): ViewModel() {

    private val _loginScreenState: MutableStateFlow<LoginScreenState> = MutableStateFlow(
        LoginScreenState()
    )
    val loginScreenState: StateFlow<LoginScreenState> = _loginScreenState

    private val _loginScreenAction: MutableSharedFlow<LoginScreenAction> = MutableSharedFlow()
    val loginScreenAction: SharedFlow<LoginScreenAction> = _loginScreenAction

    private var _changeRepasswordErrorJob: Job? = null

    fun onEvent(event: LoginEvent) {
        when(event) {
            is LoginEvent.ChangeRepassword -> {
                _changeRepasswordErrorJob?.let {
                    it.cancel()
                }
                _changeRepasswordErrorJob = viewModelScope.launch {
                    // 延迟三秒才发出值，避免不断更新
                    delay(3000L)
                    _loginScreenState.update {
                        it.copy(isLoading = false, repasswordNotSameError = false)
                    }
                }
            }
            is LoginEvent.ClickCommit -> {
                // 如果还在加载，就没有必要进来
                if (_loginScreenState.value.isLoading) return

                val username = event.userEnterInfo.username
                val password = event.userEnterInfo.password
                val repassword = event.userEnterInfo.repassword
                val agreePolicy = event.userEnterInfo.agreePolicy

                viewModelScope.launch {
                    if (username.isEmpty()) {
                        _loginScreenAction.emit(LoginScreenAction.ShowErrorSnackBar("Please enter the username"))
                    }else if (password.isEmpty()) {
                        _loginScreenAction.emit(LoginScreenAction.ShowErrorSnackBar("Please enter the password"))
                    }else if (repassword.isEmpty()) {
                        _loginScreenAction.emit(LoginScreenAction.ShowErrorSnackBar("Please enter the repassword"))
                    }else if (!agreePolicy){
                        _loginScreenAction.emit(LoginScreenAction.ShowErrorSnackBar("Please agree the policy"))
                    } else if (password != repassword) {
                        _loginScreenState.update {
                            it.copy(isLoading = false, repasswordNotSameError = true)
                        }
                    }else {
                        repo.signUp(event.userEnterInfo).collect {
                            when (it) {
                                is ResourceState.Error -> {
                                    _loginScreenAction.emit(LoginScreenAction.ShowErrorSnackBar(it.message?:"Wrong!"))
                                }
                                is ResourceState.Loading -> {
                                    _loginScreenState.update { state ->
                                        state.copy(isLoading = it.isLoading)
                                    }
                                }
                                is ResourceState.Success -> {
                                    // TODO do something else
                                    _loginScreenAction.emit(LoginScreenAction.GoHomeScreenSuccess)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}