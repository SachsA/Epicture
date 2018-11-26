package com.project.epicture.data.controllers.account

import android.content.Context
import com.project.epicture.data.controllers.utils.UtilsMethod
import com.project.epicture.data.session.SessionManagement
import org.json.JSONObject
import java.lang.Exception
import java.net.URL

class AccountDeleteMethod(contextParam: Context): UtilsMethod(contextParam) {

    private val context: Context = contextParam

    fun deleteAlbum(idAlbum: String): JSONObject? {
        val `object` = URL("https://api.imgur.com/3/account/" + SessionManagement.getAccountUsername(context) + "/album/$idAlbum")

        try {
            return getResponse(`object`, 1, "DELETE")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun deleteComment(idComment: String): JSONObject? {
        val `object` = URL("https://api.imgur.com/3/account/" + SessionManagement.getAccountUsername(context) + "/comment/$idComment")

        try {
            return getResponse(`object`, 1, "DELETE")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun deleteImage(idImage: String): JSONObject? {
        val `object` = URL("https://api.imgur.com/3/account/" + SessionManagement.getAccountUsername(context) + "/image/$idImage")

        try {
            return getResponse(`object`, 1, "DELETE")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

}