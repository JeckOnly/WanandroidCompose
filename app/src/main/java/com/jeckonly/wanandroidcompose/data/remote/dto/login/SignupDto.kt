package com.jeckonly.wanandroidcompose.data.remote.dto.login

data class SignupDto(
    val admin: Boolean = false,
    val chapterTops: List<Any> = emptyList(),
    val coinCount: Int = 0,
    val collectIds: List<Int> = emptyList(),
    val email: String = "",
    val icon: String = "",
    val id: Int = 0,
    val nickname: String = "",
    val password: String = "",
    val publicName: String = "",
    val token: String = "",
    val type: Int = 0,
    val username: String = ""
)