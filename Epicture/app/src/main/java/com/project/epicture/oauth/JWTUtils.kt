package com.project.epicture.oauth

import android.net.Uri

class JWTUtils {

    fun jwtUrl(urlEncoded: String): JWTToken? {
        val uri : Uri = Uri.parse(urlEncoded)
        val accessToken: String? = uri.getQueryParameter("access_token")
        val refreshToken: String? = uri.getQueryParameter("refresh_token")
        val expiresIn: String? = uri.getQueryParameter("expires_in")
        val accountUsername: String? = uri.getQueryParameter("account_username")
        val accountId: String? = uri.getQueryParameter("account_id")

        return JWTToken(accessToken, refreshToken, expiresIn, accountUsername, accountId)
    }
}