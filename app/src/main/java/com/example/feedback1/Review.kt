package com.example.feedback1

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "review_table")
data class Review(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val novelId: Int,
    val rating: Float,
    val description: String?
)
