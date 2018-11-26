package com.project.epicture.items.fragments.profile

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.epicture.R
import com.project.epicture.data.SessionStorage
import com.project.epicture.widgets.adapter.ImageGridAdapter
import kotlinx.android.synthetic.main.fragment_favorite.view.*

class FavoriteFragment : Fragment() {

    private lateinit var rootView: View

    companion object {
        private val TAG = GalleryFragment::class.qualifiedName

        @JvmStatic
        fun newInstance() = GalleryFragment()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            val ft = fragmentManager!!.beginTransaction()
            ft.detach(this).attach(this).commit()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rootView.grid_view_favorite.adapter = ImageGridAdapter(SessionStorage.getDataStorage().getFavoritesUser(), activity!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_favorite, container, false)
        return rootView
    }

}
