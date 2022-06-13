package com.jeckonly.wanandroidcompose.feature_signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.aschat.util.JsonUtil
import com.jeckonly.core.util.SPConstant
import com.jeckonly.core.util.SPUtil
import com.jeckonly.wanandroidcompose.WACApplication
import com.jeckonly.wanandroidcompose.data.util.ResourceState
import com.jeckonly.wanandroidcompose.domain.repository.LoginRepository
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
class SignupViewModel @Inject constructor(
    private val repo: LoginRepository
): ViewModel() {

    private val _signupScreenState: MutableStateFlow<SignupScreenState> = MutableStateFlow(
        SignupScreenState()
    )
    val signupScreenState: StateFlow<SignupScreenState> = _signupScreenState

    private val _signupScreenAction: MutableSharedFlow<SignupScreenAction> = MutableSharedFlow()
    val signupScreenAction: SharedFlow<SignupScreenAction> = _signupScreenAction

    private var _changeRepasswordErrorJob: Job? = null

    fun onEvent(event: SignupEvent) {
        when(event) {
            is SignupEvent.ChangeRepassword -> {
                _changeRepasswordErrorJob?.let {
                    it.cancel()
                }
                _changeRepasswordErrorJob = viewModelScope.launch {
                    // 延迟三秒才发出值，避免不断更新
                    delay(3000L)
                    _signupScreenState.update {
                        it.copy(isLoading = false, repasswordNotSameError = false)
                    }
                }
            }
            is SignupEvent.ClickCommit -> {
                // 如果还在加载，就没有必要进来
                if (_signupScreenState.value.isLoading) return

                val username = event.signupUserEnterInfo.username
                val password = event.signupUserEnterInfo.password
                val repassword = event.signupUserEnterInfo.repassword
                val agreePolicy = event.signupUserEnterInfo.agreePolicy

                viewModelScope.launch {
                    if (username.isEmpty()) {
                        _signupScreenAction.emit(SignupScreenAction.ShowErrorSnackBar("Please enter the username"))
                    }else if (password.isEmpty()) {
                        _signupScreenAction.emit(SignupScreenAction.ShowErrorSnackBar("Please enter the password"))
                    }else if (repassword.isEmpty()) {
                        _signupScreenAction.emit(SignupScreenAction.ShowErrorSnackBar("Please enter the repassword"))
                    }else if (!agreePolicy){
                        _signupScreenAction.emit(SignupScreenAction.ShowErrorSnackBar("Please agree the policy"))
                    } else if (password != repassword) {
                        _signupScreenState.update {
                            it.copy(isLoading = false, repasswordNotSameError = true)
                        }
                    }else {
                        repo.signUp(username, password, repassword).collect {
                            when (it) {
                                is ResourceState.Error -> {
                                    _signupScreenAction.emit(SignupScreenAction.ShowErrorSnackBar(it.message?:"Wrong!"))
                                }
                                is ResourceState.Loading -> {
                                    _signupScreenState.update { state ->
                                        state.copy(isLoading = it.isLoading)
                                    }
                                }
                                is ResourceState.Success -> {
                                    // 成功注册
                                    // 1） 下次直接进入首页

                                    // 保存账号密码
                                    SPUtil.putAndApply(WACApplication.getApplication(), SPConstant.USERNAME, username)
                                    SPUtil.putAndApply(WACApplication.getApplication(), SPConstant.PASSWORD, password)

                                    val signupSuccessInfo = it.data!!
                                    val userInfo = signupSuccessInfo.toUserInfo()
                                    // 保存用户模型
                                    SPUtil.putAndApply(WACApplication.getApplication(), SPConstant.USER_INFO, JsonUtil.any2Json(userInfo))

                                    // 标记已登录，下次直接进入
                                    SPUtil.putAndApply(WACApplication.getApplication(), SPConstant.HAD_SIGNIN, true)
                                    _signupScreenAction.emit(SignupScreenAction.GoHomeScreenSuccess)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}