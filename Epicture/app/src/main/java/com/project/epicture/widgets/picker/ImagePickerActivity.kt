package com.project.epicture.widgets.picker

import `in`.mayanknagwanshi.imagepicker.imagePicker.ImagePicker
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.project.epicture.R
import kotlinx.android.synthetic.main.activity_image_picker.*
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import `in`.mayanknagwanshi.imagepicker.imageCompression.ImageCompressionListener
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.util.Base64
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ListView
import android.widget.RelativeLayout
import com.project.epicture.data.SessionStorage
import com.project.epicture.data.controllers.account.AccountPostMethod
import com.project.epicture.widgets.adapter.AlbumListAdapter
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream


class ImagePickerActivity : AppCompatActivity() {

    private lateinit var imagePicker: ImagePicker

    private lateinit var pickerButton: RelativeLayout

    private lateinit var dialogView: View
    private lateinit var dialogList: ListView
    private lateinit var dialogFilename: EditText
    private lateinit var dialogBuilder: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_picker)
        setSupportActionBar(toolbar)

        pickerButton = findViewById(R.id.image_picker_upload)

        dialogView = View(this)
        dialogView = layoutInflater.inflate(R.layout.activity_camera_kit_dialog, null)

        dialogFilename = dialogView.findViewById(R.id.camera_kit_input)
        dialogList = dialogView.findViewById(R.id.camera_kit_list_view)
        dialogList.adapter = AlbumListAdapter(this, SessionStorage.getDataStorage().getAlbumsUser())

        dialogBuilder = onCreateBuiler()

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener{
            startActivity(parentActivityIntent)
            finish()
        }

        imagePicker = ImagePicker()
        imagePicker.withActivity(this)
                .chooseFromGallery(true)
                .chooseFromCamera(false)
                .withCompression(false)
                .start()

        pickerButton.setOnClickListener{
            imagePicker.chooseFromGallery(true)
                    .chooseFromCamera(false)
                    .withCompression(false)
                    .start()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == ImagePicker.SELECT_IMAGE && resultCode == Activity.RESULT_OK) {
            imagePicker.addOnCompressListener(object : ImageCompressionListener {
                override fun onStart() {}

                override fun onCompressed(filePath: String) { }
            })
        }
        if (data != null) {
            val filePath: String = imagePicker.getImageFilePath(data)
            if (!filePath.isNullOrEmpty()) {
                dialogBuilder.show()
                dialogList.setOnItemClickListener { _, _, position, _ ->

                    dialogFilename.hideKeyboard()

                    if (!dialogFilename.text.toString().isEmpty()) {

                        postImage(BitmapFactory.decodeFile(filePath), dialogFilename.text.toString().replace(' ', '_'),
                                SessionStorage.getDataStorage().getAlbumsUser()[position].getId())

                        dialogBuilder.dismiss()
                        startActivity(parentActivityIntent)
                        finish()
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun onCreateBuiler(): Dialog {
        return this.let {
            val builder = AlertDialog.Builder(this)
            builder.setView(dialogView)
            builder.create()
        } ?: throw IllegalStateException("Dialog cannot be null")
    }

    private fun postImage(image: Bitmap, title: String, album: String) {
        val response: JSONObject? = AccountPostMethod(this).postImage(bodyPicture(image, title))

        postInAlbum(response!!.getJSONObject("data").get("id").toString(), album)
        SessionStorage.getDataStorage().setAlbumsUser(this)
        SessionStorage.getDataStorage().setImagesUser(this)
    }

    private fun postInAlbum(picture: String, album: String) {
        AccountPostMethod(this).postImagetoAlbum(album, bodyAlbum(picture))
    }

    private fun bodyPicture(image: Bitmap, titlePicture: String): JSONObject {
        val body = JSONObject()

        body.put("image", bitmapToBase64(image))
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

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)

        return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT)
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}
