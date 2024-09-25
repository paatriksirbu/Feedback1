package com.example.feedback1

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class NovelRepository (application: Application) {
    private val novelDao: NovelDAO
    private val allNovels: LiveData<List<Novel>>
    private val executorService : ExecutorService = Executors.newFixedThreadPool(2)


    init {
        val database = NovelDatabase.getInstance(application)
        novelDao = database.novelDao()
        allNovels = novelDao.getAllNovels()
    }


}