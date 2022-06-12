package com.jeckonly.wanandroidcompose.data.remote.intercepter

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import com.jeckonly.core.util.SPUtil
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


class AddCookiesInterceptor(private val mContext: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val builder: Request.Builder = request.newBuilder()
        val cookie = getCookie(request.url.toString(), request.url.host)
        if (!TextUtils.isEmpty(cookie)) {
            builder.addHeader("Cookie", cookie)
        }
        return chain.proceed(builder.build())
    }

    private fun getCookie(url: String, host: String): String {
        val sp: SharedPreferences = mContext.getSharedPreferences(SPUtil.getSpName(mContext), Context.MODE_PRIVATE)
        if (!TextUtils.isEmpty(host) && sp.contains(host) && !TextUtils.isEmpty(sp.getString(host, ""))) {
            return sp.getString(host, "")!!
        }
        if (!TextUtils.isEmpty(url) && sp.contains(url) && !TextUtils.isEmpty(sp.getString(url, ""))) {
            return sp.getString(url, "")!!
        }
        return ""
    }
}
