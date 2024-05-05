package com.example.intents

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
        if (Intent.ACTION_SEND == receivedAction && receivedType != null) {
            when {

                receivedType.startsWith("text/") -> {
                    // Handle shared text
                    handleSharedText(receivedIntent)
                }

                receivedType.startsWith("image/") -> {
                    // Handle shared image
                    handleSharedImage(receivedIntent)
                }

                else -> {
                    // Unsupported content type
                    Toast.makeText(this, "Unsupported content type", Toast.LENGTH_SHORT).show()
                    finish() // Close the activity if content type is not supported
                }
            }
        } else {
            // No shared content found
            Toast.makeText(this, "No shared content found", Toast.LENGTH_SHORT).show()
            finish() // Close the activity if no shared content is found
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
        val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
        if (sharedText != null) {
            // Display the shared text in the text view
            textViewReceivedText.text = "Shared Text: \"$sharedText\""
            Toast.makeText(this, "Received shared text: $sharedText", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "No shared content found", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to handle shared image
    private fun handleSharedImage(intent: Intent) {
        // Retrieve the image URI from the intent
        var imageUri: Uri? = intent.getParcelableExtra(Intent.EXTRA_STREAM)

        if (imageUri != null) {

            // Display the shared image in the image view
            imageViewReceivedImage.setImageURI(imageUri)

            // Display a message indicating a shared image
            val imageDescription = getImageDescription(imageUri)
            textViewReceivedText.text = "Shared Image: $imageDescription"
            Toast.makeText(this, "Received shared image: $imageUri", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(this, "No shared image found", Toast.LENGTH_SHORT).show()
        }

    }
    private fun getImageDescription(uri: Uri): String {
        // Extract the image description from the URI (e.g., file name or path segment)
        // For Google images, you might want to extract the image name or unique identifier
        return uri.lastPathSegment ?: "Image"
    }

}