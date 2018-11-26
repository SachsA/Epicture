package com.project.epicture.widgets.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.project.epicture.R
import com.project.epicture.data.models.AlbumModel

class AlbumListAdapter(private val context: Context, private val dataSource: ArrayList<AlbumModel>) : BaseAdapter() {

    private lateinit var rootView: View
    private lateinit var inflater: LayoutInflater


    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        rootView = inflater.inflate(R.layout.item_album, null)

        val titleAlbum: TextView = rootView.findViewById(R.id.item_album_text) as TextView
        val album: AlbumModel = getItem(position) as AlbumModel

        titleAlbum.text = album.getTitle()
        return rootView
    }
}