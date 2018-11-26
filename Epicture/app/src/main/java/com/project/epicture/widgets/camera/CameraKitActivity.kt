package com.project.epicture.widgets.camera

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.project.epicture.R
import com.wonderkiln.camerakit.CameraView
import android.widget.ImageView
import android.widget.ListView
import com.project.epicture.data.SessionStorage
import com.project.epicture.data.controllers.account.AccountPostMethod
import com.project.epicture.widgets.adapter.AlbumListAdapter
import com.wonderkiln.camerakit.CameraKit
import com.wonderkiln.camerakit.CameraKitEventListener
import org.json.JSONArray
import org.json.JSONObject
import com.wonderkiln.camerakit.CameraKitVideo
import com.wonderkiln.camerakit.CameraKitImage
import com.wonderkiln.camerakit.CameraKitError
import com.wonderkiln.camerakit.CameraKitEvent

class CameraKitActivity : AppCompatActivity() {

    private lateinit var cameraView: CameraView
    private lateinit var cameraButton: ImageView
    private lateinit var cameraSwitch: ImageView
    private lateinit var cameraFlash: ImageView
    private lateinit var cameraClose: ImageView

    private lateinit var dialogView: View
    private lateinit var dialogList: ListView
    private lateinit var dialogFilename: EditText
    private lateinit var dialogBuilder: Dialog

    private var switchState: Boolean = false
    private var flashState: Int = 0

    private lateinit var byteArrayPicture: ByteArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_kit)

        byteArrayPicture = ByteArray(0)

        dialogView = View(this)
        dialogView = layoutInflater.inflate(R.layout.activity_camera_kit_dialog, null)

        dialogFilename = dialogView.findViewById(R.id.camera_kit_input)
        dialogList = dialogView.findViewById(R.id.camera_kit_list_view)
        dialogList.adapter = AlbumListAdapter(this, SessionStorage.getDataStorage().getAlbumsUser())

        dialogBuilder = onCreateBuiler()

        cameraView = findViewById(R.id.camera)
        cameraButton = findViewById(R.id.camera_kit_button)
        cameraSwitch = findViewById(R.id.camera_kit_switch)
        cameraFlash = findViewById(R.id.camera_kit_flash)
        cameraClose = findViewById(R.id.camera_kit_close)

    }

    private fun onCreateBuiler(): Dialog {
        return this.let {
            val builder = AlertDialog.Builder(this)
            builder.setView(dialogView)
            builder.create()
        } ?: throw IllegalStateException("Dialog cannot be null")
    }

    override fun onResume() {
        super.onResume()

        cameraView.start()

       cameraView.addCameraKitListener(object : CameraKitEventListener {
            override fun onVideo(p0: CameraKitVideo) {
            }

            override fun onEvent(p0: CameraKitEvent) {
            }

            override fun onImage(p0: CameraKitImage) {
                byteArrayPicture = p0.jpeg
                return takenPicture()
            }

            override fun onError(p0: CameraKitError) {
            }

        })

        cameraView.flash = CameraKit.Constants.FLASH_OFF

        cameraButton.setOnClickListener {
            takePicture()
        }
        cameraSwitch.setOnClickListener {
            switchState = !switchState
            switchCamera()
        }
        cameraFlash.setOnClickListener {
            flashCamera()
        }
        cameraClose.setOnClickListener {
            cameraView.stop()
            startActivity(parentActivityIntent)
            finish()

        }
    }

    override fun onPause() {
        cameraView.stop()
        super.onPause()
    }

    private fun takePicture() {
        cameraView.captureImage()
    }

    private fun takenPicture() {
        dialogBuilder.show()
        dialogList.setOnItemClickListener { _, _, position, _ ->
            dialogFilename.hideKeyboard()
            if (!dialogFilename.text.toString().isEmpty()) {
                postPicture(byteArrayPicture, dialogFilename.text.toString().replace(' ', '_'),
                        SessionStorage.getDataStorage().getAlbumsUser()[position].getId())
                dialogBuilder.dismiss()
                cameraView.stop()
                startActivity(parentActivityIntent)
                finish()
            }
        }
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun switchCamera() {
        when (switchState) {
            false -> cameraView.facing = CameraKit.Constants.FACING_BACK
            true -> cameraView.facing = CameraKit.Constants.FACING_FRONT
        }
    }

    private fun flashCamera() {
        when (flashState) {
            0 -> {
                flashState = 1
                cameraView.flash = CameraKit.Constants.FLASH_AUTO
                cameraFlash.setImageResource(R.drawable.button_flash_auto)
            }
            1 -> {
                flashState = 2
                cameraView.flash = CameraKit.Constants.FLASH_ON
                cameraFlash.setImageResource(R.drawable.button_flash_on)
            }
            2 -> {
                flashState = 0
                cameraView.flash = CameraKit.Constants.FLASH_OFF
                cameraFlash.setImageResource(R.drawable.button_flash_off)
            }
        }
    }

    private fun postPicture(byteArray: ByteArray, filename: String, album: String) {
        val response: JSONObject? = AccountPostMethod(this).postImage(bodyPicture(byteArray, filename))

        postInAlbum(response!!.getJSONObject("data").get("id").toString(), album)
        SessionStorage.getDataStorage().setAlbumsUser(this)
        SessionStorage.getDataStorage().setImagesUser(this)
    }


    private fun postInAlbum(picture: String, album: String) {
        AccountPostMethod(this).postImagetoAlbum(album, bodyAlbum(picture))
    }

    private fun bodyPicture(byteArray: ByteArray, titlePicture: String): JSONObject {
        val body = JSONObject()

        body.put("image", Base64.encodeToString(byteArray, Base64.DEFAULT))
        body.put("title", titlePicture + "_picture")
        body.put("description", "$titlePicture image has been upload with Epicture Application")
        body.put("name", "$titlePicture.gif")
        body.put("type", "gif")
        return body
    }

    private fun bodyAlbum(idPicture: String): JSONObject {
        val body = JSONObject()
        val arrayIds = JSONArray()

        arrayIds.put(idPicture)
        body.put("ids", arrayIds)
        return body
    }
}