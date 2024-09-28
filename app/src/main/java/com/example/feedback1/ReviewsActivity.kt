package com.example.feedback1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class ReviewsActivity : AppCompatActivity() {

    private lateinit var ratingBar: RatingBar
    private lateinit var reviewEditText: EditText
    private lateinit var submitButton: Button
    private lateinit var database: NovelDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reviews)

        database = NovelDatabase.getInstance(this)

        ratingBar = findViewById(R.id.ratingBar)
        reviewEditText = findViewById(R.id.reviewEditText)
        submitButton = findViewById(R.id.submitButton)

        submitButton.setOnClickListener {
            val rating = ratingBar.rating
            val reviewText = reviewEditText.text.toString()
            val novelId = intent.getIntExtra("novelId", -1)

            if (novelId == -1) {
                Toast.makeText(this, "Error: No se pudo obtener el ID de la novela", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val review = Review(novelId = novelId, rating = rating, description = reviewText)

            lifecycleScope.launch {
                database.reviewDao().insert(review)
                Toast.makeText(
                    this@ReviewsActivity,
                    "Reseña enviada: $rating estrellas\n$reviewText",
                    Toast.LENGTH_SHORT
                ).show()

                ratingBar.rating = 0f
                reviewEditText.text.clear()
            }
        }
    }
}








