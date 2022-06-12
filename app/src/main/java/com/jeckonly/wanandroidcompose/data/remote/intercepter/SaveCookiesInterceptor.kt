package com.jeckonly.wanandroidcompose.data.remote.intercepter

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import com.jeckonly.core.util.SPUtil
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


class SaveCookiesInterceptor(private val mContext: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response: Response = chain.proceed(request)
        //set-cookie可能为多个
        if (response.headers("set-cookie").isNotEmpty()) {
            val cookies: List<String> = response.headers("set-cookie")
            val cookie = encodeCookie(cookies)
            saveCookie(request.url.toString(), request.url.host, cookie)
        }
        return response
    }

    //整合cookie为唯一字符串
    private fun encodeCookie(cookies: List<String>): String {
        val sb = StringBuilder()
        val set: MutableSet<String> = HashSet()
        for (cookie in cookies) {
            val arr = cookie.split(";").toTypedArray()
            for (s in arr) {
                if (set.contains(s)) continue
                set.add(s)
            }
        }
        val ite: Iterator<String> = set.iterator()
        while (ite.hasNext()) {
            val cookie = ite.next()
            sb.append(cookie).append(";")
        }
        val last = sb.lastIndexOf(";")
        if (sb.length - 1 == last) {
            sb.deleteCharAt(last)
        }
        return sb.toString()
    }

    //保存cookie到本地，这里我们分别为该url和host设置相同的cookie，其中host可选
    //这样能使得该cookie的应用范围更广
    private fun saveCookie(url: String, host: String, cookies: String) {
        val sp: SharedPreferences = mContext.getSharedPreferences(SPUtil.getSpName(mContext), Context.MODE_PRIVATE)
        val editor = sp.edit()
        if (!TextUtils.isEmpty(url)) {
            editor.putString(url, cookies)
        } else {
            throw NullPointerException("url is null.")
        }
        if (!TextUtils.isEmpty(host)) {
            editor.putString(host, cookies)
        }
        editor.apply()
    }
}
