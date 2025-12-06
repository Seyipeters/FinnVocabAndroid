package com.finnvocab.app

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var database: WordDatabase
    private lateinit var wordDao: WordDao

    // Flashcard UI
    private lateinit var frontAnim: AnimatorSet
    private lateinit var backAnim: AnimatorSet
    private var isFront = true
    private var flashcardWords: List<Word> = emptyList()
    private var currentCardIndex = 0

    private lateinit var cardContainer: View
    private lateinit var cardFront: CardView
    private lateinit var cardBack: CardView
    private lateinit var tvFinnishWord: TextView
    private lateinit var tvFinnishSentence: TextView
    private lateinit var tvEnglishWord: TextView
    private lateinit var tvEnglishSentence: TextView
    private lateinit var btnPrevious: Button
    private lateinit var btnNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize database
        database = WordDatabase.getDatabase(this, lifecycleScope)
        wordDao = database.wordDao()

        initializeViews()
        setupAnimations()
        
        // Load data
        loadData()

        // Button click listeners
        btnCategories.setOnClickListener {
            startActivity(Intent(this, CategoriesActivity::class.java))
        }

        btnQuiz.setOnClickListener {
            startActivity(Intent(this, QuizActivity::class.java))
        }

        btnAddWord.setOnClickListener {
            startActivity(Intent(this, AddWordActivity::class.java))
        }
        
        // Flashcard Interactions
        cardContainer.setOnClickListener {
            flipCard()
        }

        btnNext.setOnClickListener {
            if (currentCardIndex < flashcardWords.size - 1) {
                currentCardIndex++
                updateCardContent()
                resetCardPosition()
            }
        }

        btnPrevious.setOnClickListener {
            if (currentCardIndex > 0) {
                currentCardIndex--
                updateCardContent()
                resetCardPosition()
            }
        }
    }
    
    // UI Elements defined here to be accessible in click listeners
    private lateinit var btnCategories: Button
    private lateinit var btnQuiz: Button
    private lateinit var btnAddWord: Button

    private fun initializeViews() {
        // Word of Day Elements
        val wordOfDayFinnish: TextView = findViewById(R.id.wordOfDayFinnish)
        val wordOfDayEnglish: TextView = findViewById(R.id.wordOfDayEnglish)

        // Flashcard Elements
        cardContainer = findViewById(R.id.cardContainer)
        cardFront = findViewById(R.id.cardFront)
        cardBack = findViewById(R.id.cardBack)
        tvFinnishWord = findViewById(R.id.tvFinnishWord)
        tvFinnishSentence = findViewById(R.id.tvFinnishSentence)
        tvEnglishWord = findViewById(R.id.tvEnglishWord)
        tvEnglishSentence = findViewById(R.id.tvEnglishSentence)
        btnPrevious = findViewById(R.id.btnPrevious)
        btnNext = findViewById(R.id.btnNext)
        
        // Navigation Buttons
        btnCategories = findViewById(R.id.btnCategories)
        btnQuiz = findViewById(R.id.btnQuiz)
        btnAddWord = findViewById(R.id.btnAddWord)

        // Set camera distance for 3D effect
        val scale = resources.displayMetrics.density
        cardFront.cameraDistance = 8000 * scale
        cardBack.cameraDistance = 8000 * scale
    }

    private fun setupAnimations() {
        frontAnim = AnimatorInflater.loadAnimator(this, R.animator.front_animator) as AnimatorSet
        backAnim = AnimatorInflater.loadAnimator(this, R.animator.back_animator) as AnimatorSet
    }

    private fun loadData() {
        lifecycleScope.launch {
            val allWords = wordDao.getAllWords().first()
            
            // 1. Setup Word of the Day (Top Left Mini Card)
            if (allWords.isNotEmpty()) {
                val randomWord = allWords.random()
                findViewById<TextView>(R.id.wordOfDayFinnish).text = randomWord.finnish
                findViewById<TextView>(R.id.wordOfDayEnglish).text = randomWord.english
            } else {
                findViewById<TextView>(R.id.wordOfDayFinnish).text = "No words"
                findViewById<TextView>(R.id.wordOfDayEnglish).text = "Add new"
            }

            // 2. Setup Flashcards (Main Center)
            flashcardWords = allWords
            if (flashcardWords.isNotEmpty()) {
                updateCardContent()
            } else {
                tvFinnishWord.text = "No words found"
                tvFinnishSentence.text = ""
                tvEnglishWord.text = "Add words first"
                tvEnglishSentence.text = ""
                btnNext.isEnabled = false
                btnPrevious.isEnabled = false
                cardContainer.isEnabled = false
            }
        }
    }

    private fun updateCardContent() {
        if (flashcardWords.isEmpty()) return
        
        val currentWord = flashcardWords[currentCardIndex]
        
        tvFinnishWord.text = currentWord.finnish
        tvFinnishSentence.text = currentWord.sentence
        
        tvEnglishWord.text = currentWord.english
        tvEnglishSentence.text = currentWord.sentenceEnglish.ifEmpty { "No translation available" }
        
        btnPrevious.isEnabled = currentCardIndex > 0
        btnNext.isEnabled = currentCardIndex < flashcardWords.size - 1
    }

    private fun flipCard() {
        if (isFront) {
            frontAnim.setTarget(cardFront)
            backAnim.setTarget(cardBack)
            frontAnim.start()
            
            cardBack.visibility = View.VISIBLE
            backAnim.start()
            
            isFront = false
        } else {
            frontAnim.setTarget(cardBack)
            backAnim.setTarget(cardFront)
            frontAnim.start()
            
            cardFront.visibility = View.VISIBLE
            backAnim.start()
            
            isFront = true
        }
    }

    private fun resetCardPosition() {
        if (frontAnim.isRunning) frontAnim.cancel()
        if (backAnim.isRunning) backAnim.cancel()

        isFront = true
        
        cardFront.rotationY = 0f
        cardFront.alpha = 1f
        cardFront.visibility = View.VISIBLE
        
        cardBack.rotationY = -90f
        cardBack.alpha = 0f
        cardBack.visibility = View.GONE
    }
}
