package com.project.epicture.items.fragments.profile

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.epicture.R
import com.project.epicture.data.SessionStorage
import com.project.epicture.widgets.album.AlbumContentActivity
import com.project.epicture.widgets.adapter.AlbumListAdapter
import kotlinx.android.synthetic.main.fragment_album.view.*

class AlbumFragment : Fragment() {

    private lateinit var rootView: View

    companion object {
        private val TAG = AlbumFragment::class.qualifiedName

        @JvmStatic
        fun newInstance() = AlbumFragment()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            val ft = fragmentManager!!.beginTransaction()
            ft.detach(this).attach(this).commit()
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rootView.fragment_album_list.adapter = AlbumListAdapter(context!!, SessionStorage.getDataStorage().getAlbumsUser())
        rootView.fragment_album_list.setOnItemClickListener { _, _, position, _ ->
            openAlbumContent(SessionStorage.getDataStorage().getAlbumsUser()[position].getId())
        }
    }

    private fun openAlbumContent(id: String) {
        val intent: Intent = Intent(context, AlbumContentActivity::class.java)

        SessionStorage.getDataStorage().setAlbumContent(context!!, id)
        startActivity(intent)
        activity!!.finish()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_album, container, false)
        return rootView
    }

}
