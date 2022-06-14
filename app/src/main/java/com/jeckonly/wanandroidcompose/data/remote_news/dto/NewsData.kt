package com.jeckonly.wanandroidcompose.data.remote_news.dto

data class NewsData(
    val articles: List<Article> = emptyList(),
    val status: String = "",
    val totalResults: Int = 0,
    val code:String = "",// 错误时提供
    val message:String = ""// 错误时提供
)