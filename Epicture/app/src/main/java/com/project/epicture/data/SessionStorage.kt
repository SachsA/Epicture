package com.project.epicture.data

object SessionStorage {

    private lateinit var data: DataStorage

    fun getDataStorage(): DataStorage {
        return data
    }

    fun setDataStorage(dataStorage: DataStorage) {
        data = dataStorage
    }
}