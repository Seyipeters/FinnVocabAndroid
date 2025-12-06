package com.finnvocab.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class CategoriesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories) 
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Categories"
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
