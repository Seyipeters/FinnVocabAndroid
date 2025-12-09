package com.finnvocab.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This layout now contains all the text and image views directly.
        setContentView(R.layout.activity_about)

        // Setup the toolbar and back button
        val toolbar: Toolbar = findViewById(R.id.toolbar_about)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // Handle the back button press
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
