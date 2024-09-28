package com.example.feedback1

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NovelDAO {

   @Insert
   fun insert(novel: Novel)

   @Delete
   fun delete(novel: Novel)

   @Query("SELECT * FROM novels")
   fun getAllNovels(): LiveData<List<Novel>>

}