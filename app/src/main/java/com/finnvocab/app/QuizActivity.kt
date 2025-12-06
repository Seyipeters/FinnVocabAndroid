package com.finnvocab.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class QuizActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Quiz"
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
