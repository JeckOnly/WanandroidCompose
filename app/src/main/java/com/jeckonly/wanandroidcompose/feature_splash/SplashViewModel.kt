package com.jeckonly.wanandroidcompose.feature_splash

import androidx.lifecycle.ViewModel
import com.jeckonly.wanandroidcompose.feature_splash.event.SplashEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(

): ViewModel() {



    fun onEvent(event: SplashEvent) {
        when(event) {
            is SplashEvent.ToHome -> {

            }
            is SplashEvent.ToLogin -> {

            }
        }
    }
}