package com.jeckonly.wanandroidcompose.feature_splash.event

sealed class SplashEvent {
    object ToHome: SplashEvent()
    object ToLogin: SplashEvent()
}
