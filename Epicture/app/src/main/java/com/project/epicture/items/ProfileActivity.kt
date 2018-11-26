package com.project.epicture.items

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import android.widget.TextView
import com.project.epicture.R
import com.project.epicture.data.SessionStorage
import com.project.epicture.items.fragments.profile.AlbumFragment
import com.project.epicture.items.fragments.profile.FavoriteFragment
import com.project.epicture.items.fragments.profile.GalleryFragment
import com.project.epicture.widgets.adapter.ProfilePagerAdapter
import com.squareup.picasso.Picasso
import android.content.Intent
import android.webkit.CookieManager
import android.webkit.WebView
import com.project.epicture.MainActivity
import com.project.epicture.data.session.SessionManagement
import android.util.Log

class ProfileActivity : BaseActivity() {

    private lateinit var name: TextView
    private lateinit var cover: ImageView
    private lateinit var avatar: ImageView
    private lateinit var reputation: TextView
    private lateinit var points: TextView
    private lateinit var appBar: AppBarLayout
    private lateinit var appToolbar: Toolbar

    private lateinit var pagerAdapter: ProfilePagerAdapter
    private lateinit var pager: ViewPager
    private lateinit var tabLayout: TabLayout

    override val contentViewId: Int
        get() = R.layout.activity_profile

    override val navigationMenuItemId: Int
        get() = R.id.navigation_profile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cover = findViewById(R.id.profile_cover)
        avatar = findViewById(R.id.profile_avatar)
        name = findViewById(R.id.profile_name)
        reputation = findViewById(R.id.profile_reputation)
        points = findViewById(R.id.profile_points)

        appBar = findViewById(R.id.appbar_profile_bar)
        appToolbar = findViewById(R.id.appbar_toolbar_profile_bar)
        appToolbar.inflateMenu(R.menu.logout)
        appToolbar.setOnMenuItemClickListener { it ->
            when (it.itemId) {
                R.id.action_logout -> {
                    getDialogBuilder().show()
                    return@setOnMenuItemClickListener true
                }
                else -> {
                    return@setOnMenuItemClickListener false
                }
            }
        }

        appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, offset ->
            run {
                when (Math.abs(offset)) {
                    appBarLayout.totalScrollRange -> appToolbar.title = "Profile"
                    0 -> appToolbar.title = ""
                }
            }
        })

        createInfoUser()
        createTabLayout()
    }

    private fun createTabLayout() {
        pagerAdapter = ProfilePagerAdapter(supportFragmentManager)
        pager = findViewById(R.id.profile_view_pager)
        tabLayout = findViewById(R.id.profile_tablayout)

        pagerAdapter.addFragments(GalleryFragment(), "GALLERY")
        pagerAdapter.addFragments(FavoriteFragment(), "FAVORITES")
        pagerAdapter.addFragments(AlbumFragment(), "ALBUMS")

        pager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(pager)
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"))
    }

    private fun createInfoUser() {
        val pointsString: String = SessionStorage.getDataStorage().getInfosUser().getReputationPoints() + " POINTS"

        Picasso.with(this).load(SessionStorage.getDataStorage().getInfosUser().getCover()).into(cover)
        Picasso.with(this).load(SessionStorage.getDataStorage().getInfosUser().getAvatar()).into(avatar)
        name.text = SessionStorage.getDataStorage().getInfosUser().getName()
        reputation.text = SessionStorage.getDataStorage().getInfosUser().getReputation()
        points.text = pointsString
    }
}