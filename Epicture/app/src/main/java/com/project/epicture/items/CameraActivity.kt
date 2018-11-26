package com.project.epicture.items

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.RelativeLayout
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.project.epicture.R
import com.project.epicture.widgets.camera.CameraKitActivity
import com.project.epicture.widgets.picker.ImagePickerActivity
import kotlinx.android.synthetic.main.activity_camera.*

class CameraActivity : BaseActivity() {

    private lateinit var takePicture: RelativeLayout
    private lateinit var importPicture: RelativeLayout

    private lateinit var imagePickerIntent: Intent
    private lateinit var permissionListener: PermissionListener

    override val contentViewId: Int
        get() = R.layout.activity_camera

    override val navigationMenuItemId: Int
        get() = R.id.navigation_camera

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        permissionListener = requestPermissionListener()
        imagePickerIntent = Intent(this, ImagePickerActivity::class.java)

        takePicture = findViewById(R.id.take_picture)
        importPicture = findViewById(R.id.import_picture)
    }

    override fun onResume() {
        super.onResume()
        var intent: Intent

        takePicture.setOnClickListener {
            intent = Intent(this, CameraKitActivity::class.java)
            startActivity(intent)
            finish()
        }

        importPicture.setOnClickListener {
            Dexter.withActivity(this)
                    .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .withListener(permissionListener)
                    .check()

        }
    }

    private fun requestPermissionListener(): PermissionListener {

        return object: PermissionListener {
            override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                startActivity(imagePickerIntent)
                finish()
            }

            override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {}

            override fun onPermissionDenied(response: PermissionDeniedResponse?) {}
        }
    }
}
