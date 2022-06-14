package com.jeckonly.wanandroidcompose.data.remote_news

import com.jeckonly.wanandroidcompose.data.remote.dto.common.RemoteDto
import com.jeckonly.wanandroidcompose.data.remote.dto.login.SigninDto
import com.jeckonly.wanandroidcompose.data.remote.dto.login.SignupDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface NewsApi {




    companion object {
        const val BASE_URL = "https://newsapi.org/v2"


        /**
         * NOTE 需要注意的参数
         *
         * searchIn: title,content  不能 title,content,
         * domains: 可以bbc.co.uk, techcrunch.com,
         * from 这个值的下限是上一个月的同一天
         * to 测试发现这个值比from小依然有数据，from和to是不合理的值（32号）依然有数据
         * pageSize 设置成在系统设置里更改，其他在搜索界面更改
         */
        const val SEARCH_EVERYTHING = "$BASE_URL/everything"
    }
}