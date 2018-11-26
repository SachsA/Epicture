package com.project.epicture.data.controllers.utils

import android.content.Context
import com.project.epicture.data.models.ImgurModel
import com.project.epicture.data.session.SessionManagement
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL


open class UtilsMethod(contextParam: Context) {

    private val context: Context = contextParam

    fun getResponse(`object`: URL, headerVal: Int, method: String, body: JSONObject? = null): JSONObject? {
        val connection = `object`.openConnection() as HttpURLConnection

        connection.requestMethod = method
        getHeader(connection, headerVal, context)

        if (method == "POST")
            connection.doOutput = true
        if (body != null) {
            val outputStreamWriter = OutputStreamWriter(connection.outputStream)
            outputStreamWriter.write(body.toString())
            outputStreamWriter.flush()
            outputStreamWriter.close()
        }

        val responseCode = connection.responseCode
        val stringBuilder = StringBuilder()

        return if (responseCode == 200) {
            BufferedReader(InputStreamReader(connection.inputStream)).use {
                var inputLine = it.readLine()
                while (inputLine != null) {
                    stringBuilder.append(inputLine)
                    inputLine = it.readLine()
                }
                it.close()
            }
            JSONObject(stringBuilder.toString())
        } else {
            return null
        }
    }

    private fun getHeader(connection: HttpURLConnection, headerVal: Int, context: Context) {
        val header: String = SessionManagement.getHeaderToken(context).toString()

        val prefixToken: String = SessionManagement.getPrefixToken(context).toString()
        val accessToken: String = SessionManagement.getAccessToken(context).toString()

        val prefixClientId: String = ImgurModel.getPrefixClientId()
        val clientId: String = ImgurModel.getClientId()

        when (headerVal) {
            1 -> {
                connection.setRequestProperty(header, prefixToken + accessToken)
                connection.setRequestProperty("Content-Type", "application/json")
            }
            2 -> {
                connection.setRequestProperty(header, prefixClientId + clientId)
                connection.setRequestProperty("Content-Type", "application/json")
            }
            3 -> {
                connection.setRequestProperty(header, prefixToken + accessToken)
                connection.setRequestProperty(header, prefixClientId + clientId)
                connection.setRequestProperty("Content-Type", "application/json")
            }
        }
    }

}