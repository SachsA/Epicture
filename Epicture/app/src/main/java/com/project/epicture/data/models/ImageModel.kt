package com.project.epicture.data.models

class ImageModel(private var id: String) {

    private lateinit var title: String
    private lateinit var description: String
    private lateinit var url: String
    private var favorite: Boolean = false
    private var views: Int = 0

    fun getId(): String {
        return id
    }

    fun getTitle(): String {
        return title
    }

    fun getDescription(): String {
        return description
    }

    fun getUrl(): String {
        return url
    }

    fun getViews(): Int {
        return views
    }

    fun getFavorite(): Boolean {
        return favorite
    }

    fun setId(value: String) {
        id = value
    }

    fun setTitle(value: String) {
        title = value
    }

    fun setDescription(value: String) {
        description = value
    }

    fun setUrl(value: String) {
        url = value
    }

    fun setViews(value: Int) {
        views = value
    }

    fun setFavorite(value: Boolean) {
        favorite = value
    }
}