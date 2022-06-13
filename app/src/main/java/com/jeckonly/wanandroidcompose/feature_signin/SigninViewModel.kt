package com.jeckonly.wanandroidcompose.feature_signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeckonly.wanandroidcompose.data.util.ResourceState
import com.jeckonly.wanandroidcompose.domain.repository.LoginRepository
import com.jeckonly.wanandroidcompose.feature_signin.event.SigninEvent
import com.jeckonly.wanandroidcompose.feature_signin.model.SigninScreenAction
import com.jeckonly.wanandroidcompose.feature_signin.model.SigninScreenState
import com.jeckonly.wanandroidcompose.feature_signup.event.SignupEvent
import com.jeckonly.wanandroidcompose.feature_signup.model.SignupScreenAction
import com.jeckonly.wanandroidcompose.feature_signup.model.SignupScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SigninViewModel @Inject constructor(
    private val repo: LoginRepository
): ViewModel() {

    private val _signinScreenState: MutableStateFlow<SigninScreenState> = MutableStateFlow(
        SigninScreenState()
    )
    val signinScreenState: StateFlow<SigninScreenState> = _signinScreenState

    private val _signinScreenAction: MutableSharedFlow<SigninScreenAction> = MutableSharedFlow()
    val signinScreenAction: SharedFlow<SigninScreenAction> = _signinScreenAction


    fun onEvent(event: SigninEvent) {
        when(event) {
            is SigninEvent.ClickCommit -> {
                // 如果还在加载，就没有必要进来
                if (_signinScreenState.value.isLoading) return

                val username = event.signinUserEnterInfo.username
                val password = event.signinUserEnterInfo.password
                val rememberMe = event.signinUserEnterInfo.rememberMe
                // todo remember

                viewModelScope.launch {
                    if (username.isEmpty()) {
                        _signinScreenAction.emit(SigninScreenAction.ShowErrorSnackBar("Please enter the username"))
                    }else if (password.isEmpty()) {
                        _signinScreenAction.emit(SigninScreenAction.ShowErrorSnackBar("Please enter the password"))
                    }else {
                        repo.signIn(username, password).collect {
                            when (it) {
                                is ResourceState.Error -> {
                                    _signinScreenAction.emit(SigninScreenAction.ShowErrorSnackBar(it.message?:"Wrong!"))
                                }
                                is ResourceState.Loading -> {
                                    _signinScreenState.update { state ->
                                        state.copy(isLoading = it.isLoading)
                                    }
                                }
                                is ResourceState.Success -> {
                                    // TODO do something else
                                    _signinScreenAction.emit(SigninScreenAction.GoHomeScreenSuccess)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}