package com.jeckonly.wanandroidcompose.data.remote_news.intercepter

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.text.TextUtils
import com.jeckonly.core.util.SPUtil
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


/**
 * 添加header
 */
class AddHeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        // 添加header
        val original = chain.request();
        val request = original.newBuilder()
            .header("Authorization", "7e9499bd37214887938512963550841e")
            .method(original.method, original.body)
            .build();
        return chain.proceed(request);
    }
}
