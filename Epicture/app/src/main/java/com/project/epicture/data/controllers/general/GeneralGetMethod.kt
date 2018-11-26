package com.project.epicture.data.controllers.general

import android.content.Context
import com.project.epicture.data.controllers.utils.UtilsMethod
import org.json.JSONObject
import java.lang.Exception
import java.net.URL

class GeneralGetMethod(contextParam: Context) : UtilsMethod(contextParam) {

    fun getGallery(section: String): JSONObject? {
        val `object` = URL("https://api.imgur.com/3/gallery/$section")

        try {
            return getResponse(`object`, 1, "GET")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getTags(): JSONObject? {
        val `object` = URL("https://api.imgur.com/3/tags")

        try {
            return getResponse(`object`, 1, "GET")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getByTag(section: String, name: String): JSONObject? {
        val `object` = URL("https://api.imgur.com/3/gallery/t/$name/$section")

        try {
            return getResponse(`object`, 1, "GET")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getByResearch(value: String): JSONObject? {
        val research: String = value.replace(' ', '+')
        val `object` = URL("https://api.imgur.com/3/gallery/search?q_all=$research")

        try {
            return getResponse(`object`, 1, "GET")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getImage(imageId: String): JSONObject? {
        val `object` = URL("https://api.imgur.com/3/image/$imageId")

        try {
            return getResponse(`object`, 2, "GET")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getComment(commentId: String): JSONObject? {
        val `object` = URL("https://api.imgur.com/3/comment/$commentId")

        try {
            return getResponse(`object`, 2, "GET")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getRepliesForComment(commentId: String): JSONObject? {
        val `object` = URL("https://api.imgur.com/3/comment/$commentId/replies")

        try {
            return getResponse(`object`, 2, "GET")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getImageOrAlbumVotes(id: String): JSONObject? {
        val `object` = URL("https://api.imgur.com/3/gallery/$id/votes")

        try {
            return getResponse(`object`, 2, "GET")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

}