package com.project.epicture.items

import android.app.Dialog
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.MenuItem
import android.webkit.CookieManager
import android.webkit.WebView
import com.project.epicture.MainActivity
import com.project.epicture.R
import com.project.epicture.data.SessionStorage
import com.project.epicture.data.session.SessionManagement


abstract class BaseActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var navigation: BottomNavigationView

    internal abstract val contentViewId: Int

    internal abstract val navigationMenuItemId: Int

   private lateinit var dialogBuilder: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentViewId)

        dialogBuilder = onCreateBuiler()
        navigation = findViewById(R.id.navigation_main)
        navigation.setOnNavigationItemSelectedListener(this)
    }

    override fun onStart() {
        super.onStart()
        updateNavigationBarState()
    }

    public override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        navigation.postDelayed({
            when (item.getItemId()) {
                R.id.navigation_explore -> {
                    if (dialogBuilder.isShowing)
                        dialogBuilder.dismiss()
                    startActivity(Intent(this, ExploreActivity::class.java))
                }
                R.id.navigation_camera -> {
                    if (dialogBuilder.isShowing)
                        dialogBuilder.dismiss()
                    startActivity(Intent(this, CameraActivity::class.java))
                }
                R.id.navigation_profile -> {
                    if (dialogBuilder.isShowing)
                        dialogBuilder.dismiss()
                    startActivity(Intent(this, ProfileActivity::class.java))
                }
            }
            finish()
        }, 300)
        return true
    }

    private fun updateNavigationBarState() {
        val actionId = navigationMenuItemId
        selectBottomNavigationBarItem(actionId)
    }

    private fun selectBottomNavigationBarItem(itemId: Int) {
        val item = navigation.menu.findItem(itemId)
        item.isChecked = true
    }


    private fun onCreateBuiler(): Dialog {
        return this.let {
            val builder = android.app.AlertDialog.Builder(this)
                    .setPositiveButton("Yes") { dialog, _ ->
                        dialog.dismiss()
                        logout()
                    }
                    .setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setTitle("Logout")
                    .setMessage("Do you really want to logout ?")
            builder.create()
        } ?: throw IllegalStateException("Dialog cannot be null")
    }

    fun getDialogBuilder(): Dialog {
        return dialogBuilder
    }

    private fun logout() {
        val webView: WebView = WebView(this)
        val cookieManager: CookieManager = CookieManager.getInstance()
        val intent: Intent = Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        SessionStorage.getDataStorage().clearDataStorage()
        SessionManagement.clearSession(this)
        webView.clearCache(true)
        webView.clearHistory()

        cookieManager.flush()
        cookieManager.removeAllCookies { it ->
            Log.d("ProfileActivity", "Cookie removed: " + it.toString())
        }

        cookieManager.removeSessionCookies{ it ->
            Log.d("ProfileActivity", "Session Cookie removed: " + it.toString())
        }
        cookieManager.flush()

        startActivity(intent)
        finish()
    }
}