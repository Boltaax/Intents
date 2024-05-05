package com.example.intents

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var buttonShareText: Button
    private lateinit var buttonShareImage: Button
    private lateinit var buttonOpenYoutube: Button
    private lateinit var buttonOpenEmail: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        initView()

        // Setup click listeners for share buttons
        setupShareButton()
        setupShareImageButton()

        // Setup click listener for open YouTube button
        buttonOpenYoutube.setOnClickListener {
            openYoutube()
        }

        // Setup click listener for open Email button
        buttonOpenEmail.setOnClickListener {
            openEmail()
        }
    }

    // Function to initialize views
    private fun initView() {
        buttonShareText = findViewById(R.id.buttonShareText)
        buttonShareImage = findViewById(R.id.buttonShareImage)
        buttonOpenYoutube = findViewById(R.id.buttonOpenYoutube)
        buttonOpenEmail = findViewById(R.id.buttonOpenEmail)
    }

    // Function to setup click listener for text share button
    private fun setupShareButton() {
        buttonShareText.setOnClickListener {
            showTextInputDialog()
        }
    }

    // Function to setup click listener for image share button
    private fun setupShareImageButton() {
        buttonShareImage.setOnClickListener {
            // Here you can implement image loading or selection logic
            // For this example, we'll use a static image URI
            val imageUri = Uri.parse("android.resource://$packageName/${R.drawable.android_logo}")
            shareImage(imageUri)
        }
    }

    // Function to share text
    private fun shareText(text: String) {
        val intent = Intent(this, SecondActivity::class.java).apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        startActivity(intent)
    }

    // Function to share an image
    private fun shareImage(imageUri: Uri) {
        val intent = Intent(this, SecondActivity::class.java).apply {
            action = Intent.ACTION_SEND
            type = "image/*"
            putExtra(Intent.EXTRA_STREAM, imageUri)
        }
        startActivity(intent)
    }

    // Function to open YouTube
    private fun openYoutube() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/"))
        startActivity(intent)
    }

    // Function to open Email app
    private fun openEmail() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf("recipient@example.com"))
            putExtra(Intent.EXTRA_SUBJECT, "Subject")
            putExtra(Intent.EXTRA_TEXT, "Email body")
        }

        // Create a chooser to let the user pick from available email apps
        val chooser = Intent.createChooser(intent, "Send Email")
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(chooser)
        } else {
            Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to show a dialog allowing user to enter text
    private fun showTextInputDialog() {
        val editText = EditText(this)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Enter Text to Share")
            .setView(editText)
            .setPositiveButton("Share") { dialog, _ ->
                val sharedText = editText.text.toString()
                shareText(sharedText)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialog.show()
    }
}
