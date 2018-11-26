package com.project.epicture.data.models

class AlbumModel(private var id: String) {

    private lateinit var title: String
    private lateinit var description: String

    fun getId(): String {
        return id
    }

    fun getTitle(): String {
        return title
    }

    fun getDescription(): String {
        return description
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
}