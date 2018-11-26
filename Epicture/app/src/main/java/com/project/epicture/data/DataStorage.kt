package com.project.epicture.data

import android.content.Context
import com.project.epicture.data.controllers.account.AccountGetMethod
import com.project.epicture.data.controllers.account.AccountPostMethod
import com.project.epicture.data.controllers.general.GeneralGetMethod
import com.project.epicture.data.models.AlbumModel
import com.project.epicture.data.models.ImageModel
import com.project.epicture.data.models.TagModel
import com.project.epicture.data.models.UserModel
import org.json.JSONArray
import org.json.JSONObject

class DataStorage {

    private var albumsUser: ArrayList<AlbumModel> = ArrayList()
    private var favoritesUser: ArrayList<ImageModel> = ArrayList()
    private var imagesUser: ArrayList<ImageModel> = ArrayList()
    private lateinit var infosUser: UserModel
    private var albumContent: ArrayList<ImageModel> = ArrayList()
    private var tagsApp: ArrayList<TagModel> = ArrayList()
    private var fluxApp: ArrayList<ImageModel> = ArrayList()
    private var fluxAppCopy: ArrayList<ImageModel> = ArrayList()

    fun getAlbumsUser(): ArrayList<AlbumModel> {
        return albumsUser
    }

    fun getImagesUser(): ArrayList<ImageModel> {
        return imagesUser
    }

    fun getInfosUser(): UserModel {
        return infosUser
    }

    fun getFavoritesUser(): ArrayList<ImageModel> {
        return favoritesUser
    }

    fun getAlbumContent(): ArrayList<ImageModel> {
        return albumContent
    }

    fun getTagImages(): ArrayList<TagModel> {
        return tagsApp
    }

    fun getFluxApp(): ArrayList<ImageModel> {
        return fluxApp
    }

    fun getFLuxSearched(): ArrayList<ImageModel> {
        return fluxApp
    }

    fun getImagesInTag(value: String?): ArrayList<ImageModel> {
        if (!value!!.isEmpty()) {
            for (i in 0 until tagsApp.size) {
                if (tagsApp[i].getName() == value) {
                    return tagsApp[i].getImages()
                }
            }
        }
        return ArrayList()
    }

    private fun setInfosUser(context: Context) {
        val arrayObj: JSONObject = AccountGetMethod(context).getAccountBase()!!.getJSONObject("data")

        infosUser = UserModel(arrayObj.get("id").toString())
        infosUser.setName(arrayObj.get("url").toString())
        infosUser.setAvatar(arrayObj.get("avatar").toString())
        infosUser.setCover(arrayObj.get("cover").toString())
        infosUser.setReputation(arrayObj.get("reputation_name").toString())
        infosUser.setReputationPoints(arrayObj.get("reputation").toString())
    }

    fun setFavoritesUser(context: Context) {
        var array: JSONArray
        var imageModel: ImageModel
        var link: String
        var obj: JSONObject
        val data: JSONArray = AccountGetMethod(context).getAccountGalleryFavorites()!!.getJSONArray("data")

        if (!favoritesUser.isEmpty())
            favoritesUser.clear()

        for (i in 0 until data.length()) {
            if (data.getJSONObject(i).has("images")) {
                array = data.getJSONObject(i).getJSONArray("images")
                for (j in 0 until array.length()) {
                    obj = data.getJSONObject(j)
                    imageModel = ImageModel(obj.get("id").toString())
                    link = if (obj.has("mp4"))
                        obj.get("mp4").toString()
                    else
                        obj.get("link").toString()
                    imageModel.setUrl(link)
                    imageModel.setFavorite(true)
                    imageModel.setViews(obj.get("views") as Int)
                    favoritesUser.add(imageModel)
                }
            } else if (data.getJSONObject(i).has("link") || data.getJSONObject(i).has("mp4")) {
                obj = data.getJSONObject(i)
                imageModel = ImageModel(obj.get("id").toString())
                imageModel.setTitle(obj.get("title").toString())
                link = if (obj.has("mp4"))
                    obj.get("mp4").toString()
                else
                    obj.get("link").toString()
                imageModel.setUrl(link)
                imageModel.setFavorite(true)
                imageModel.setViews(obj.get("views") as Int)
                favoritesUser.add(imageModel)
            }
        }
    }

    fun setAlbumsUser(context: Context) {

        var obj: JSONObject
        val data: JSONArray = AccountGetMethod(context).getAccountAlbums()!!.getJSONArray("data")

        if (!albumsUser.isEmpty())
            albumsUser.clear()

        for (i in 0 until data.length()) {
            obj = data.getJSONObject(i)
            albumsUser.add(AlbumModel(obj.get("id").toString()))
            albumsUser[i].setTitle(obj.get("title").toString())
            albumsUser[i].setDescription(obj.get("description").toString())
        }
    }

    fun setImagesUser(context: Context) {

        var link: String
        var obj: JSONObject
        val data: JSONArray = AccountGetMethod(context).getAccountImages()!!.getJSONArray("data")

        if (!imagesUser.isEmpty())
            imagesUser.clear()

        for (i in 0 until data.length()) {
            obj = data.getJSONObject(i)
            imagesUser.add(ImageModel(obj.get("id").toString()))
            imagesUser[i].setTitle(obj.get("title").toString())
            link = if (obj.has("mp4"))
                obj.get("mp4").toString()
            else
                obj.get("link").toString()
            imagesUser[i].setUrl(link)
            imagesUser[i].setFavorite(obj.get("favorite") as Boolean)
            imagesUser[i].setViews(obj.get("views") as Int)
        }
    }

    fun setAlbumContent(context: Context, album: String) {
        val data: JSONArray = AccountGetMethod(context).getAccountAlbum(album)!!.getJSONObject("data").getJSONArray("images")
        var obj: JSONObject
        var link: String

        for (i in 0 until data.length()) {
            obj = data.getJSONObject(i)
            albumContent.add(ImageModel(obj.get("id").toString()))
            albumContent[i].setTitle(obj.get("title").toString())
            link = if (obj.has("mp4"))
                obj.get("mp4").toString()
            else
                obj.get("link").toString()
            albumContent[i].setUrl(link)
            albumContent[i].setFavorite(obj.get("favorite") as Boolean)
            albumContent[i].setViews(obj.get("views") as Int)
        }
    }

    private fun setTagApp(context: Context) {
        var obj: JSONObject
        val data: JSONArray = GeneralGetMethod(context).getTags()!!.getJSONObject("data").getJSONArray("tags")

        if (!tagsApp.isEmpty())
            tagsApp.clear()

        for (i in 0 until 12) {
            obj = data.getJSONObject(i)
            tagsApp.add(TagModel(obj.get("name").toString()))
            tagsApp[i].setDescription(obj.get("description").toString())
            tagsApp[i].setItems(obj.get("total_items").toString())
            tagsApp[i].setFollowers(obj.get("followers").toString())
            setTagImages(context, i)
        }
    }

    private fun setValuesImages(context: Context, data: JSONArray, flux: ArrayList<ImageModel>,
                                fluxCopy: ArrayList<ImageModel>? = null) {
        var array: JSONArray
        var obj: JSONObject
        var link: String
        var favorite: Boolean
        var imageModel: ImageModel
        var i = 0

        while (i < data.length() && flux.size <= 200) {
            favorite = data.getJSONObject(i).get("favorite") as Boolean
            if (data.getJSONObject(i).has("images")) {
                array = data.getJSONObject(i).getJSONArray("images")
                for (j in 0 until array.length()) {
                    obj = array.getJSONObject(j)
                    imageModel = ImageModel(obj.get("id").toString())
                    imageModel.setTitle(obj.get("title").toString())
                    link = if (obj.has("mp4"))
                        obj.get("mp4").toString()
                    else
                        obj.get("link").toString()
                    imageModel.setUrl(link)
                    if (favorite)
                        AccountPostMethod(context).postFavoriteOrUnfavorite(imageModel.getId())
                    imageModel.setFavorite(favorite)
                    imageModel.setViews(obj.get("views") as Int)
                    flux.add(imageModel)
                    fluxCopy?.add(imageModel)
                }
            } else if (data.getJSONObject(i).has("link") || data.getJSONObject(i).has("mp4")) {
                obj = data.getJSONObject(i)
                imageModel = ImageModel(obj.get("id").toString())
                imageModel.setTitle(obj.get("title").toString())
                link = if (obj.has("mp4"))
                    obj.get("mp4").toString()
                else
                    obj.get("link").toString()
                imageModel.setUrl(link)
                if (favorite)
                    AccountPostMethod(context).postFavoriteOrUnfavorite(imageModel.getId())
                imageModel.setFavorite(favorite)
                imageModel.setViews(obj.get("views") as Int)
                flux.add(imageModel)
                fluxCopy?.add(imageModel)
            }
            i++
        }
    }

    private fun setTagImages(context: Context, index: Int) {
        val flux: TagModel = tagsApp[index]
        val data: JSONArray = GeneralGetMethod(context).getByTag("top", flux.getName())!!.getJSONObject("data").getJSONArray("items")
        setValuesImages(context, data, flux.getImages())
    }

    fun setFluxApp(context: Context, search: String? = null) {
        val data: JSONArray = if (!search.isNullOrEmpty())
            GeneralGetMethod(context).getByResearch(search)!!.getJSONArray("data")
        else
            GeneralGetMethod(context).getGallery("top")!!.getJSONArray("data")

        if (!fluxApp.isEmpty())
            fluxApp.clear()

        if (!fluxAppCopy.isEmpty())
            fluxAppCopy.clear()

        setValuesImages(context, data, fluxApp, fluxAppCopy)
    }

    fun setFluxAppCopy() {
        fluxApp = fluxAppCopy
    }

    fun setFluxAppFavorites(image: ImageModel) {
        for (i in 0 until fluxApp.size) {
            if (image.getId() == fluxApp[i].getId()) {
                fluxApp[i].setFavorite(image.getFavorite())
            }
        }
    }

    fun setImageFavorites(image: ImageModel) {
        if (favoritesUser.size == 0)
            favoritesUser.add(image)
        else {
            for (i in 0 until favoritesUser.size) {
                if (imageExist(image.getId())) {
                    favoritesUser.removeAt(i)
                    return
                } else {
                    favoritesUser.add(image)
                }
            }
        }
    }

    fun imageExist(imageId: String): Boolean {
        for (i in 0 until favoritesUser.size) {
            if (imageId == favoritesUser[i].getId()) {
                return true
            }
        }
        return false
    }

    fun initDataStorage(context: Context) {
        this.setInfosUser(context)
        this.setFavoritesUser(context)
        this.setImagesUser(context)
        this.setAlbumsUser(context)
        this.setTagApp(context)
        this.setFluxApp(context)
    }

    fun clearDataStorage() {
        imagesUser.clear()
        albumsUser.clear()
        favoritesUser.clear()
        infosUser.clear()
    }
}