package com.project.epicture.items

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import com.project.epicture.R
import com.project.epicture.data.SessionStorage
import com.project.epicture.data.models.TagModel
import com.project.epicture.items.fragments.explore.ExploreFragment
import com.project.epicture.widgets.adapter.ExplorePagerAdapter
import android.app.SearchManager
import android.content.Context
import android.support.v7.widget.SearchView
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.project.epicture.data.DataStorage

class ExploreActivity : BaseActivity() {

    private lateinit var appBar: AppBarLayout
    private lateinit var appToolbar: Toolbar

    private lateinit var pagerAdapter: ExplorePagerAdapter
    private lateinit var pager: ViewPager
    private lateinit var tabLayout: TabLayout

    private lateinit var searchManager: SearchManager
    private lateinit var searchView: SearchView

    override val contentViewId: Int
        get() = R.layout.activity_explore

    override val navigationMenuItemId: Int
        get() = R.id.navigation_explore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent.getIntExtra("oneTime", 0) != 0)
            initializeDataStorage()

        appBar = findViewById(R.id.app_bar_explore)
        appToolbar = findViewById(R.id.toolbar)

        appBar.elevation = 0F
        appToolbar.elevation = 0F
        appToolbar.title = "Explore"
        appToolbar.inflateMenu(R.menu.search)

        searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = appToolbar.menu.findItem(R.id.action_search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        createTabLayout()
        queryListener()
    }

    private fun createTabLayout() {
        val tags: ArrayList<TagModel> = SessionStorage.getDataStorage().getTagImages()

        pagerAdapter = ExplorePagerAdapter(supportFragmentManager)
        pager = findViewById(R.id.explore_view_pager)
        tabLayout = findViewById(R.id.explore_tab_layout)

        pagerAdapter.addFragments(ExploreFragment.newInstance("all"), "all")
        for (i in 0 until tags.size) {
            pagerAdapter.addFragments(ExploreFragment.newInstance(tags[i].getName()), tags[i].getName())
        }
        pager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(pager)
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"))
    }

    private fun initializeDataStorage() {
        SessionStorage.setDataStorage(DataStorage())
        SessionStorage.getDataStorage().initDataStorage(this)
    }

    private fun queryListener() {

        val queryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(text: String): Boolean {
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                if (query == "" || query.isEmpty())
                    setQuery("all")
                else
                    setQuery(query)
                return true
            }
        }

        searchView.setOnQueryTextListener(queryTextListener)
    }

    private fun setQuery(query: String): Boolean {
        val allFragment = pagerAdapter.getItem(0) as ExploreFragment
        window.decorView.rootView.hideKeyboard()
        pager.currentItem = 0

        if (!query.isEmpty() && query != "all") {
            SessionStorage.getDataStorage().setFluxApp(this, query)
            allFragment.updateFragment(query)
        } else {
            SessionStorage.getDataStorage().setFluxAppCopy()
            allFragment.updateFragment("all")
        }
        pager.adapter!!.notifyDataSetChanged()
        return true
    }


    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}