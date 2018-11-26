package com.project.epicture

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.StrictMode
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.WebView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.project.epicture.data.DataStorage
import com.project.epicture.data.SessionStorage
import com.project.epicture.data.models.ImgurModel
import com.project.epicture.data.session.SessionManagement
import com.project.epicture.items.ExploreActivity
import com.project.epicture.oauth.WebClientClt
import java.lang.IllegalStateException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())

        val progressBar: ProgressBar = findViewById(R.id.progress_main_bar)
        val buttonLogin: RelativeLayout = findViewById(R.id.project_login)
        val webClientClt = WebClientClt(this)
        val webView = onCreateWebView(webClientClt)
        val dialog = onCreateDialog(webView)

        progressBar.visibility = View.VISIBLE

        Handler().postDelayed({
            webView.loadUrl(ImgurModel.getAuthorizeUrl(true))
            if (SessionManagement.isTokenExpired(this)) {
                webClientClt.setOneTime(1)
                progressBar.visibility = View.GONE
                buttonLogin.visibility = View.VISIBLE
                buttonLogin.setOnClickListener { dialog.show() }
            } else {
                initializeDataStorage()
                progressBar.visibility = View.GONE

                val intent = Intent(this, ExploreActivity::class.java)
                intent.putExtra("oneTime", 0)
                startActivity(intent)
                finish()
            }
        }, 2500)
    }

    private fun initializeDataStorage() {
        SessionStorage.setDataStorage(DataStorage())
        SessionStorage.getDataStorage().initDataStorage(this)
    }

    private fun onCreateWebView(webClientClt: WebClientClt): WebView {
        val webView = WebView(this)
        webView.clearCache(true)
        webView.settings.builtInZoomControls = true
        webView.webViewClient = webClientClt
        return webView
    }

    private fun onCreateDialog(webView: WebView): Dialog {
        return this.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(webView)
                    .setNegativeButton(R.string.action_cancel) { dialog, _ -> dialog.dismiss() }
            builder.create()
        } ?: throw IllegalStateException("Dialog cannot be null")
    }
}
