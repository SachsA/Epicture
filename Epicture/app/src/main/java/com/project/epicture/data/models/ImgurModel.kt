package com.project.epicture.data.models


object ImgurModel {

    private val URL_ADD_CLIENT: String = "https://api.imgur.com/oauth2/addclient"
    private val URL_AUTHORIZE: String = "https://api.imgur.com/oauth2/authorize"
    private val URL_TOKEN: String = "https://api.imgur.com/oauth2/token"
    private val URL_SECRET: String = "https://api.imgur.com/oauth2/secret"
    private val URL_REDIRECTION: String = "epicture://epicture/callback"

    private val RESPONSE_TYPE_TOKEN: String = "token"
    private val RESPONSE_TYPE_CODE: String = "code"
    private val RESPONSE_TYPE_PIN: String = "pin"

    private val CLIENT_NAME: String = "Epicture"
    private val CLIENT_ID: String = "6dcc23e430fd242"
    private val CLIENT_SECRET: String = "e9644da4b1db3d90fd44c190627bbaf420789d83"

    private val PREFIX_CLIENT_ID: String = "Client-ID "

    fun getClientId(): String {
        return this.CLIENT_ID
    }

    fun getClientSecret(): String {
        return this.CLIENT_SECRET
    }

    fun getClientName(): String {
        return this.CLIENT_NAME
    }

    fun getAddClientUrl(): String {
        return this.URL_ADD_CLIENT
    }

    fun getAuthorizeUrl(clientId: Boolean): String {
        if (clientId) {
            return this.URL_AUTHORIZE + "?client_id=" + this.CLIENT_ID + "&response_type=" + this.RESPONSE_TYPE_TOKEN
        }
        return this.URL_AUTHORIZE
    }

    fun getTokenUrl(): String {
        return this.URL_TOKEN
    }

    fun getSecretUrl(): String {
        return this.URL_SECRET
    }

    fun getRedirectUrl(): String {
        return this.URL_REDIRECTION
    }

    fun getResponseToken(): String {
        return this.RESPONSE_TYPE_TOKEN
    }

    fun getResponseCode(): String {
        return this.RESPONSE_TYPE_CODE
    }

    fun getResponsePin(): String {
        return this.RESPONSE_TYPE_PIN
    }

    fun getPrefixClientId(): String {
        return this.PREFIX_CLIENT_ID
    }
}