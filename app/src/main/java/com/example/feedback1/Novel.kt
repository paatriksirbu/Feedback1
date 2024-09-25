package com.example.feedback1

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "novel_table")
data class Novel(
    val title: String,
    val author: String,
    val year: Int,
    val synopsis: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)