package com.project.epicture.items.fragments.explore

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pedromassango.doubleclick.DoubleClick
import com.pedromassango.doubleclick.DoubleClickListener
import com.project.epicture.R
import com.project.epicture.data.SessionStorage
import com.project.epicture.widgets.adapter.ImageGridAdapter
import kotlinx.android.synthetic.main.fragment_explore.view.*
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import com.project.epicture.data.controllers.account.AccountPostMethod
import com.project.epicture.data.models.ImageModel


class ExploreFragment : Fragment() {

    private lateinit var adapter: ImageGridAdapter

    private lateinit var rootView: View
    private var name: String? = null

    companion object {
        private val TAG = ExploreFragment::class.qualifiedName

        @JvmStatic
        fun newInstance(name: String) = ExploreFragment().apply {
            arguments = Bundle().apply {
                putString("name", name)
            }
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        arguments?.getString("name")?.let {
            name = it
        }
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

        if (!name.equals("all")) {
            adapter = ImageGridAdapter(SessionStorage.getDataStorage().getImagesInTag(name), activity!!)
            rootView.grid_view_explore.adapter = adapter
        } else {
            adapter = ImageGridAdapter(SessionStorage.getDataStorage().getFluxApp(), activity!!)
            rootView.grid_view_explore.adapter = adapter
        }

        rootView.grid_view_explore.onItemClickListener = AdapterView.OnItemClickListener { parent, viewItem, position, id ->
            setFavorite(parent.getItemAtPosition(position) as ImageModel)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_explore, container, false)
        return rootView
    }

    fun updateFragment(query: String) {
        if (!name.equals("all"))
            adapter.setData(SessionStorage.getDataStorage().getFLuxSearched())
        else
            adapter.setData(SessionStorage.getDataStorage().getFluxApp())
    }

    private fun setFavorite(item: ImageModel) {
        item.setFavorite(!item.getFavorite())
        AccountPostMethod(context!!).postFavoriteOrUnfavorite(item.getId())
        SessionStorage.getDataStorage().setFluxAppFavorites(item)
        SessionStorage.getDataStorage().setImageFavorites(item)
        adapter.notifyDataSetChanged()
    }
}
