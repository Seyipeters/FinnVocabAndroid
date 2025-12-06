package com.finnvocab.app

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class AddWordActivity : AppCompatActivity() {

    private lateinit var database: WordDatabase
    private lateinit var wordDao: WordDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_word)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Add New Word"

        database = WordDatabase.getDatabase(this, lifecycleScope)
        wordDao = database.wordDao()

        val finnishInput: EditText = findViewById(R.id.finnishInput)
        val englishInput: EditText = findViewById(R.id.englishInput)
        val categoryInput: EditText = findViewById(R.id.categoryInput)
        val sentenceInput: EditText = findViewById(R.id.sentenceInput)
        val sentenceEnglishInput: EditText = findViewById(R.id.sentenceEnglishInput)
        val btnSave: Button = findViewById(R.id.btnSave)

        btnSave.setOnClickListener {
            val finnish = finnishInput.text.toString().trim()
            val english = englishInput.text.toString().trim()
            val category = categoryInput.text.toString().trim().ifEmpty { "Custom" }
            val sentence = sentenceInput.text.toString().trim()
            val sentenceEnglish = sentenceEnglishInput.text.toString().trim()

            if (finnish.isEmpty() || english.isEmpty()) {
                Toast.makeText(this, "Please fill in Finnish and English fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newWord = Word(
                finnish = finnish,
                english = english,
                category = category,
                sentence = sentence,
                sentenceEnglish = sentenceEnglish
            )

            lifecycleScope.launch {
                wordDao.insertWord(newWord)
                Toast.makeText(this@AddWordActivity, "Word added!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
