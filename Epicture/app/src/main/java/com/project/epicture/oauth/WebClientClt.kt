package com.project.epicture.oauth

import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.ViewGroup
import android.webkit.*
import android.widget.Toast
import com.project.epicture.R
import com.project.epicture.data.DataStorage
import com.project.epicture.data.SessionStorage
import com.project.epicture.data.models.ImgurModel
import com.project.epicture.data.session.SessionManagement
import com.project.epicture.items.ExploreActivity


class WebClientClt internal constructor(private val activity: Activity) : WebViewClient() {

    private val jwtUtils: JWTUtils = JWTUtils()
    private var oneTime: Int = 0

    override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
        return super.shouldInterceptRequest(view, request)
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {

        val url: String = request?.url.toString()
        val intent: Intent = Intent(activity, ExploreActivity::class.java)

        if (url.contains(ImgurModel.getAuthorizeUrl(true), false))
            return false
        intent.putExtra("oneTime", oneTime)
        activity.startActivity(intent)
        activity.finish()
        return true
    }

    override fun onPageFinished(view: WebView?, url: String) {
        val jwtToken: JWTToken? = jwtUtils.jwtUrl(url.replace('#', '?'))
        SessionManagement.setAccessToken(activity, jwtToken)
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
    }

    override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
        Toast.makeText(activity, "Got Error! $error", Toast.LENGTH_SHORT).show()
    }

    fun setOneTime(value: Int) {
        oneTime = value
    }
}