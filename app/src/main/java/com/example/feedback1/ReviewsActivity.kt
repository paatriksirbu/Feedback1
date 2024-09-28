package com.example.feedback1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ReviewsActivity : AppCompatActivity() {

    private lateinit var ratingBar: RatingBar
    private lateinit var reviewEditText: EditText
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reviews)

        ratingBar = findViewById(R.id.ratingBar)
        reviewEditText = findViewById(R.id.reviewEditText)
        submitButton = findViewById(R.id.submitButton)

        submitButton.setOnClickListener {
            val rating = ratingBar.rating
            val reviewText = reviewEditText.text.toString()

            Toast.makeText(
                this,
                "Reseña enviada: $rating estrellas\n$reviewText",
                Toast.LENGTH_SHORT
            ).show()

            ratingBar.rating = 0f
            reviewEditText.text.clear()
        }
    }
}








