package com.project.epicture.widgets.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.R.attr.data



class ExplorePagerAdapter(fragmentManagerParam: FragmentManager) : FragmentPagerAdapter(fragmentManagerParam) {

    private var fragmentItems: ArrayList<Fragment> = ArrayList()
    private var fragmentTitles: ArrayList<String> = ArrayList()

    fun addFragments(items: Fragment, title: String) {
        fragmentItems.add(items)
        fragmentTitles.add(title)
    }

    override fun getItem(position: Int): Fragment {
        return fragmentItems[position]
    }

    override fun getCount(): Int {
        return fragmentItems.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitles[position]
    }
}