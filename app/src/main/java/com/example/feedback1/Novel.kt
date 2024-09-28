package com.example.feedback1

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "novels")
data class Novel(
    val titulo: String,
    val autor: String,
    val year: Int,
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}