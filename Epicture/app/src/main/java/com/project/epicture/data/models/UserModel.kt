package com.project.epicture.data.models

class UserModel(private var id: String) {

    private lateinit var name: String
    private lateinit var cover: String
    private lateinit var avatar: String
    private lateinit var reputation: String
    private lateinit var points: String

    fun getId(): String {
        return id
    }

    fun getName(): String {
        return name
    }

    fun getCover(): String {
        return cover
    }

    fun getAvatar(): String {
        return avatar
    }

    fun getReputation(): String {
        return reputation
    }

    fun getReputationPoints(): String {
        return points
    }

    fun setId(value: String) {
        id = value
    }

    fun setName(value: String) {
        name = value
    }

    fun setCover(value: String) {
        cover = value
    }

    fun setAvatar(value: String) {
        avatar = value
    }

    fun setReputation(value: String) {
        reputation = value
    }
    fun setReputationPoints(value: String) {
        points = value
    }

    fun clear() {
        name = ""
        cover = ""
        avatar = ""
        reputation = ""
        points = ""
    }
}