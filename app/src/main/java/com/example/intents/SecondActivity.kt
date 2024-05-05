package com.example.intents

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    private lateinit var textViewReceivedText: TextView
    private lateinit var imageViewReceivedImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // Initialize views
        initView()

        // Retrieve the intent that started this activity
        val receivedIntent = intent
        val receivedAction = receivedIntent.action
        val receivedType = receivedIntent.type

        // Check the action and type of the received intent
        if (receivedAction == Intent.ACTION_SEND && receivedType?.startsWith("image/") == true) {
            // Handle shared image
            handleSharedImage(receivedIntent)
        } else {
            // Handle shared text
            handleSharedText(receivedIntent)
        }
    }

    // Function to initialize views
    private fun initView() {
        textViewReceivedText = findViewById(R.id.textViewReceivedText)
        imageViewReceivedImage = findViewById(R.id.imageViewReceivedImage)
    }

    // Function to handle shared text
    private fun handleSharedText(intent: Intent) {
        // Retrieve the shared text from the intent
        val sharedText = intent.getStringExtra("sharedText")
        if (sharedText != null) {
            // Display the shared text in the text view
            textViewReceivedText.text = "Shared Text: \"$sharedText\""
        } else {
            // Display a message if no shared text is found
            textViewReceivedText.text = "No shared text found"
        }
    }

    // Function to handle shared image
    private fun handleSharedImage(intent: Intent) {
        // Retrieve the image URI from the intent
        val imageUri: Uri? = intent.getParcelableExtra("sharedImage")
        if (imageUri != null) {
            // Display the shared image in the image view
            imageViewReceivedImage.setImageURI(imageUri)
            // Display a message indicating a shared image
            textViewReceivedText.text = "Shared Image: \"${imageUri.lastPathSegment}\""
        } else {
            // Display a message if no shared image is found
            textViewReceivedText.text = "No shared image found"
        }
    }

}
