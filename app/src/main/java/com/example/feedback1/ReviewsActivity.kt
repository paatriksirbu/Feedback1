package com.example.feedback1

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class ReviewsActivity : AppCompatActivity() {

    private lateinit var ratingBar: RatingBar
    private lateinit var reviewEditText: EditText
    private lateinit var submitButton: Button
    private lateinit var database: NovelDatabase
    private lateinit var spinnerNovels: Spinner

    private val novels: MutableList<Novel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reviews)

        database = NovelDatabase.getInstance(this)

        ratingBar = findViewById(R.id.ratingBar)
        reviewEditText = findViewById(R.id.reviewEditText)
        submitButton = findViewById(R.id.submitButton)
        spinnerNovels = findViewById(R.id.spinnerNovels)

        loadNovels()

        submitButton.setOnClickListener {
            val rating = ratingBar.rating
            val reviewText = reviewEditText.text.toString()
            val selectedNovel = spinnerNovels.selectedItem as? Novel

            if (selectedNovel != null) {
                val review = Review(novelId = selectedNovel.id, rating = rating, description = reviewText)

                lifecycleScope.launch {
                    database.reviewDao().insert(review)
                    Toast.makeText(
                        this@ReviewsActivity,
                        "Reseña enviada: $rating estrellas\n$reviewText",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Limpiar los campos después de enviar la reseña
                    ratingBar.rating = 0f
                    reviewEditText.text.clear()
                }
            } else {
                Toast.makeText(this, "Por favor, selecciona una novela", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadNovels(){
        lifecycleScope.launch{
            novels.clear()
            novels.addAll(database.novelDao().getAllNovels())

            val titulos = novels.map { it.titulo }.toTypedArray()
            val adapter = ArrayAdapter(this@ReviewsActivity, android.R.layout.simple_spinner_item, titulos)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerNovels.adapter = adapter
        }
    }
}








