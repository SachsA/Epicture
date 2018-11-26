package com.project.epicture.widgets.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.SystemClock
import android.se.omapi.Session
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.pedromassango.doubleclick.DoubleClick
import com.pedromassango.doubleclick.DoubleClickListener
import com.project.epicture.R
import com.project.epicture.data.controllers.account.AccountPostMethod
import com.project.epicture.data.models.ImageModel
import com.squareup.picasso.Picasso



class ImageGridAdapter(private var list: ArrayList<ImageModel>, private val context: Context) : BaseAdapter() {

    private lateinit var rootView: View
    private lateinit var inflater: LayoutInflater

    private lateinit var itemGrid: RelativeLayout
    private lateinit var imageView: ImageView
    private lateinit var image: ImageModel
    private lateinit var videoView: VideoView
    private lateinit var iconView: ImageView
    private lateinit var textView: TextView

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(data: ArrayList<ImageModel>) {
        list = data
        this.notifyDataSetChanged()
    }

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        rootView = inflater.inflate(R.layout.item_grid, null)

        itemGrid = rootView.findViewById(R.id.item_grid_parent)
        imageView = rootView.findViewById(R.id.grid_image)
        image = getItem(position) as ImageModel
        videoView = rootView.findViewById(R.id.grid_video)
        iconView = rootView.findViewById(R.id.grid_icon)
        textView = rootView.findViewById(R.id.grid_numbers_view)

        videoView.setZOrderOnTop(true)

        if (image.getUrl().endsWith(".mp4")) {
            videoView.setOnPreparedListener {
                mp -> mp.isLooping = true
                mp.setVolume(0f, 0f)
            }
            videoView.setZOrderOnTop(false)
            val uri = Uri.parse(image.getUrl())
            videoView.setVideoURI(uri)
            videoView.start()
        } else {
            Picasso.with(context).load(image.getUrl()).into(imageView)
        }

        if (image.getFavorite()) {
            textView.text = ""
            iconView.setImageResource(R.drawable.ic_heart_fill_red)
        } else {
            textView.text = image.getViews().toString()
        }

        return rootView
    }
}