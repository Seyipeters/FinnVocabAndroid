package com.finnvocab.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class GrammarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grammar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Grammar"
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
