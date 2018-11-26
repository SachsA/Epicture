package com.project.epicture.data.session

import android.content.Context
import android.webkit.WebView
import com.project.epicture.oauth.JWTToken
import com.project.epicture.oauth.WebClientClt
import java.util.*

object SessionManagement {

    fun setAccessToken(context: Context, jwtToken: JWTToken?) {
        val sharedPreferences = context.getSharedPreferences("SessionPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("access_token", jwtToken!!.getAccessToken())
        editor.putString("refresh_token", jwtToken.getRefreshToken())
        editor.putString("expires_in", jwtToken.getExpiresIn().toString())
        editor.putString("prefix_token", jwtToken.getPrefixToken())
        editor.putString("header_token", jwtToken.getHeaderToken())
        editor.putString("account_username", jwtToken.getAccountUsername().toString())
        editor.putString("account_id", jwtToken.getAccountId().toString())
        editor.apply()
    }

    fun getAccessToken(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("SessionPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("access_token", null)
    }

    fun getRefreshToken(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("SessionPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("refresh_token", null)
    }

    fun getExpiresIn(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("SessionPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("expires_in", null)
    }

    fun getPrefixToken(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("SessionPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("prefix_token", null)
    }

    fun getHeaderToken(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("SessionPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("header_token", null)
    }

    fun getAccountUsername(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("SessionPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("account_username", null)
    }

    fun getAccountId(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("SessionPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("account_id", null)
    }

    fun isTokenExpired(context: Context): Boolean {
        if (this.getAccessToken(context).isNullOrEmpty()) {
            return true
        }
        val expiresIn = Date().time + Date(this.getExpiresIn(context)!!.toLong()).time
        return expiresIn <= Date().time
    }

    fun clearSession(context: Context) {
        val sharedPreferences = context.getSharedPreferences("SessionPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}