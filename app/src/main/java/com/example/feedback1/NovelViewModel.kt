package com.example.feedback1

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class NovelViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : NovelRepository = NovelRepository(application)
    private val allNovels : List<Novel> = repository.getAllNovels()

    fun insert(novel: Novel) = repository.insert(novel)

    fun delete(novel: Novel) = repository.delete(novel)

    fun getAllNovels(): List<Novel> = allNovels
}

