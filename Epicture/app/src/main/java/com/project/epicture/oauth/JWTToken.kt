package com.project.epicture.oauth

class JWTToken constructor(private var accessToken: String?, private var refreshToken: String?, private var expiresIn: String?,
                           private var accountUsername: String?, private var accountId: String?, private var headerToken: String? = "Authorization",
                           private var prefixToken: String = "Bearer ") {

    fun getAccessToken(): String? {
        return this.accessToken
    }

    fun setAccessToken(value: String?) {
        this.accessToken = value
    }

    fun getRefreshToken(): String? {
        return this.refreshToken
    }

    fun setRefreshToken(value: String?) {
        this.refreshToken = value
    }

    fun getExpiresIn(): String? {
        return this.expiresIn
    }

    fun setExpiresIn(value: String?) {
        this.expiresIn = value
    }

    fun getAccountUsername(): String? {
        return this.accountUsername
    }

    fun setAccountUsername(value: String?) {
        this.accountUsername = value
    }

    fun getAccountId(): String? {
        return this.accountId
    }

    fun setAccountId(value: String?) {
        this.accountId = value
    }

    fun getHeaderToken(): String? {
        return this.headerToken
    }

    fun setHeaderToken(value: String?) {
        this.headerToken = value
    }


    fun getPrefixToken(): String {
        return this.prefixToken
    }

    fun setPrefixToken(value: String) {
        this.prefixToken = value
    }
}