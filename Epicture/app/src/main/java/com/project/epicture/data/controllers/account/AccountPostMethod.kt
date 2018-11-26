package com.project.epicture.data.controllers.account

import android.content.Context
import com.project.epicture.data.controllers.utils.UtilsMethod
import org.json.JSONObject
import java.lang.Exception
import java.net.URL

class AccountPostMethod(contextParam: Context): UtilsMethod(contextParam) {

    fun postFavoriteOrUnfavorite(idImage: String): JSONObject? {
        val `object` = URL("https://api.imgur.com/3/image/$idImage/favorite")

        try {
            return getResponse(`object`, 1, "POST")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun postFavoriteAlbum(idAlbum: String): JSONObject? {
        val `object` = URL("https://api.imgur.com/3/album/$idAlbum/favorite")

        try {
            return getResponse(`object`, 1, "POST")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun postVoteForComment(idComment: String, vote: String): JSONObject? {
        val `object` = URL("https://api.imgur.com/3/comment/$idComment/vote/$vote")

        try {
            return getResponse(`object`, 1, "POST")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun postImage(body: JSONObject): JSONObject? {

        val `object` = URL("https://api.imgur.com/3/image")

        try {
            return getResponse(`object`, 1, "POST", body)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun postImagetoAlbum(idAlbum: String, body: JSONObject): JSONObject? {

        val `object` = URL("https://api.imgur.com/3/album/$idAlbum/add")

        try {
            return getResponse(`object`, 1, "POST", body)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}