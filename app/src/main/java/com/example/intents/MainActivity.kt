package com.example.intents

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var buttonShareText: Button
    private lateinit var buttonShareImage: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        initView()

        // Set up click listeners for share buttons
        setupShareButton()
        setupShareImageButton()
    }

    // Function to initialize views
    private fun initView() {
        buttonShareText = findViewById(R.id.buttonShareText)
        buttonShareImage = findViewById(R.id.buttonShareImage)
    }

    // Function to set up click listener for text share button
    private fun setupShareButton() {
        buttonShareText.setOnClickListener {
            // Display a dialog to allow user to enter text to share
            showTextInputDialog()
        }
    }

    // Function to set up click listener for image share button
    private fun setupShareImageButton() {
        buttonShareImage.setOnClickListener {
            // Load or select an image to share (using a static image URI for this example)
            val imageUri = Uri.parse("android.resource://$packageName/${R.drawable.android_logo}")
            shareImage(imageUri)
        }
    }

    // Function to display a dialog allowing the user to enter text
    private fun showTextInputDialog() {
        val editText = EditText(this)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Enter Text to Share")
            .setView(editText)
            .setPositiveButton("Share") { dialog, _ ->
                val sharedText = editText.text.toString()
                // Call shareText function to share the entered text
                shareText(sharedText)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialog.show()
    }

    // Function to share text using an intent
    private fun shareText(text: String) {
        val intent = Intent(this, SecondActivity::class.java).apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra("sharedText", text)
        }
        startActivity(intent)
    }

    // Function to share an image using an intent
    private fun shareImage(imageUri: Uri) {
        val intent = Intent(this, SecondActivity::class.java).apply {
            action = Intent.ACTION_SEND
            type = "image/*"
            putExtra("sharedImage", imageUri)
        }
        startActivity(intent)
    }
}
