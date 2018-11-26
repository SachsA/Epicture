package com.project.epicture.data.models

class TagModel(private var name: String) {

    private lateinit var description: String
    private lateinit var items: String
    private lateinit var followers: String
    private var images: ArrayList<ImageModel> = ArrayList()

    fun getName(): String {
        return name
    }

    fun getDescription(): String {
        return description
    }

    fun getFollowers(): String {
        return followers
    }

    fun getItems(): String {
        return items
    }

    fun getImages(): ArrayList<ImageModel> {
        return images
    }

    fun setImages(value: ArrayList<ImageModel>) {
        images = value
    }

    fun setName(value: String) {
        name = value
    }

    fun setDescription(value: String) {
        description = value
    }

    fun setItems(value: String) {
        items = value
    }

    fun setFollowers(value: String) {
        followers = value
    }
}