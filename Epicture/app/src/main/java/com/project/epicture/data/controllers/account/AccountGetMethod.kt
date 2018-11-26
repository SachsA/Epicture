package com.project.epicture.data.controllers.account

import android.content.Context
import com.project.epicture.data.controllers.utils.UtilsMethod
import com.project.epicture.data.session.SessionManagement
import org.json.JSONObject
import java.lang.Exception
import java.net.URL

class AccountGetMethod(contextParam: Context): UtilsMethod(contextParam) {

    private val context: Context = contextParam

    fun getAccountGalleryFavorites(): JSONObject? {
        val `object` = URL("https://api.imgur.com/3/account/" + SessionManagement.getAccountUsername(context) + "/favorites/")

        try {
            return getResponse(`object`, 1, "GET")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getAccountBase(): JSONObject? {
        val `object` = URL("https://api.imgur.com/3/account/" + SessionManagement.getAccountUsername(context))

        try {
            return getResponse(`object`, 1, "GET")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getAccountSubmissions(): JSONObject? {
        val `object` = URL("https://api.imgur.com/3/account/" + SessionManagement.getAccountUsername(context) + "/submissions/")

        try {
            return getResponse(`object`, 1, "GET")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getAccountAvatar(): JSONObject? {
        val `object` = URL("https://api.imgur.com/3/account/" + SessionManagement.getAccountUsername(context) + "/avatar")

        try {
            return getResponse(`object`, 1, "GET")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getAccountSettings(): JSONObject? {
        val `object` = URL("https://api.imgur.com/3/account/" + SessionManagement.getAccountUsername(context) + "/settings")

        try {
            return getResponse(`object`, 1, "GET")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getAccountGalleryProfile(): JSONObject? {
        val `object` = URL("https://api.imgur.com/3/account/" + SessionManagement.getAccountUsername(context) + "/settings")

        try {
            return getResponse(`object`, 1, "GET")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getAccountAlbums(): JSONObject? {
        val `object` = URL("https://api.imgur.com/3/account/" + SessionManagement.getAccountUsername(context) + "/albums/")

        try {
            return getResponse(`object`, 1, "GET")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getAccountAlbum(albumId: String): JSONObject? {
        val `object` = URL("https://api.imgur.com/3/account/" + SessionManagement.getAccountUsername(context) + "/album/$albumId")

        try {
            return getResponse(`object`, 1, "GET")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getAccountAlbumIds(): JSONObject? {
        val `object` = URL("https://api.imgur.com/3/account/" + SessionManagement.getAccountUsername(context) + "/albums/ids/")

        try {
            return getResponse(`object`, 1, "GET")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getAccountComments(): JSONObject? {
        val `object` = URL("https://api.imgur.com/3/account/" + SessionManagement.getAccountUsername(context) + "/comments/newest/")

        try {
            return getResponse(`object`, 1, "GET")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getAccountComment(commentId: String): JSONObject? {
        val `object` = URL("https://api.imgur.com/3/account/" + SessionManagement.getAccountUsername(context) + "/comment/$commentId")

        try {
            return getResponse(`object`, 1, "GET")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getAccountCommentsIds(): JSONObject? {
        val `object` = URL("https://api.imgur.com/3/account/" + SessionManagement.getAccountUsername(context) + "/comments/ids/newest/")

        try {
            return getResponse(`object`, 1, "GET")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getAccountReplies(): JSONObject? {
        val `object` = URL("https://api.imgur.com/3/account/" + SessionManagement.getAccountUsername(context) + "/notifications/replies")

        try {
            return getResponse(`object`, 1, "GET")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getAccountImages(): JSONObject? {
        val `object` = URL("https://api.imgur.com/3/account/" + SessionManagement.getAccountUsername(context) + "/images")

        try {
            return getResponse(`object`, 1, "GET")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getAccountImage(idImage: String): JSONObject? {
        val `object` = URL("https://api.imgur.com/3/account/" + SessionManagement.getAccountUsername(context) + "/image/$idImage")

        try {
            return getResponse(`object`, 1, "GET")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getAccountImagesId(): JSONObject? {
        val `object` = URL("https://api.imgur.com/3/account/" + SessionManagement.getAccountUsername(context) + "/images/ids")

        try {
            return getResponse(`object`, 1, "GET")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}