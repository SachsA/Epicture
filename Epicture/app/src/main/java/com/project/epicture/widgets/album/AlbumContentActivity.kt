package com.project.epicture.widgets.album

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.GridView
import com.project.epicture.R
import com.project.epicture.data.SessionStorage
import com.project.epicture.widgets.adapter.ImageGridAdapter
import kotlinx.android.synthetic.main.activity_album_content.*

class AlbumContentActivity : AppCompatActivity() {

    private lateinit var gridAlbumContent: GridView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_content)
        setSupportActionBar(toolbar)

        gridAlbumContent = findViewById(R.id.grid_view_album)
        gridAlbumContent.adapter = ImageGridAdapter(SessionStorage.getDataStorage().getAlbumContent(), this)

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener{
            startActivity(parentActivityIntent)
            finish()
        }
    }
}
