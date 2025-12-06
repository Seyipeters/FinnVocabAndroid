package com.finnvocab.app

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class FlashcardsActivity : AppCompatActivity() {

    private lateinit var database: WordDatabase
    private lateinit var wordDao: WordDao
    
    private lateinit var frontAnim: AnimatorSet
    private lateinit var backAnim: AnimatorSet
    
    private var isFront = true
    private var words: List<Word> = emptyList()
    private var currentIndex = 0

    // UI Elements
    private lateinit var cardContainer: View
    private lateinit var cardFront: CardView
    private lateinit var cardBack: CardView
    
    private lateinit var tvFinnishWord: TextView
    private lateinit var tvFinnishSentence: TextView
    private lateinit var tvEnglishWord: TextView
    private lateinit var tvEnglishSentence: TextView
    private lateinit var tvProgress: TextView
    
    private lateinit var btnPrevious: Button
    private lateinit var btnNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flashcards)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Flashcards"

        // Initialize Database
        database = WordDatabase.getDatabase(this, lifecycleScope)
        wordDao = database.wordDao()

        initializeViews()
        setupAnimations()
        loadWords()
        
        // Card Flip Click Listener
        cardContainer.setOnClickListener {
            flipCard()
        }

        // Navigation Buttons
        btnNext.setOnClickListener {
            if (currentIndex < words.size - 1) {
                currentIndex++
                updateCardContent()
                resetCardPosition()
            }
        }

        btnPrevious.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
                updateCardContent()
                resetCardPosition()
            }
        }
    }

    private fun initializeViews() {
        cardContainer = findViewById(R.id.cardContainer)
        cardFront = findViewById(R.id.cardFront)
        cardBack = findViewById(R.id.cardBack)
        
        tvFinnishWord = findViewById(R.id.tvFinnishWord)
        tvFinnishSentence = findViewById(R.id.tvFinnishSentence)
        tvEnglishWord = findViewById(R.id.tvEnglishWord)
        tvEnglishSentence = findViewById(R.id.tvEnglishSentence)
        tvProgress = findViewById(R.id.tvProgress)
        
        btnPrevious = findViewById(R.id.btnPrevious)
        btnNext = findViewById(R.id.btnNext)

        // Set camera distance for 3D effect
        val scale = resources.displayMetrics.density
        cardFront.cameraDistance = 8000 * scale
        cardBack.cameraDistance = 8000 * scale
    }

    private fun setupAnimations() {
        frontAnim = AnimatorInflater.loadAnimator(this, R.animator.front_animator) as AnimatorSet
        backAnim = AnimatorInflater.loadAnimator(this, R.animator.back_animator) as AnimatorSet
    }

    private fun loadWords() {
        lifecycleScope.launch {
            words = wordDao.getAllWords().first()
            if (words.isNotEmpty()) {
                updateCardContent()
            } else {
                tvFinnishWord.text = "No words found"
                tvFinnishSentence.text = ""
                tvEnglishWord.text = "Add words first"
                tvEnglishSentence.text = ""
                tvProgress.text = "0 / 0"
                btnNext.isEnabled = false
                btnPrevious.isEnabled = false
                cardContainer.isEnabled = false
            }
        }
    }

    private fun updateCardContent() {
        val currentWord = words[currentIndex]
        
        tvFinnishWord.text = currentWord.finnish
        tvFinnishSentence.text = currentWord.sentence
        
        tvEnglishWord.text = currentWord.english
        tvEnglishSentence.text = currentWord.sentenceEnglish.ifEmpty { "No translation available" }
        
        tvProgress.text = "Card ${currentIndex + 1} of ${words.size}"
        
        btnPrevious.isEnabled = currentIndex > 0
        btnNext.isEnabled = currentIndex < words.size - 1
    }

    private fun flipCard() {
        if (isFront) {
            frontAnim.setTarget(cardFront)
            backAnim.setTarget(cardBack)
            
            // Start flipping front out
            frontAnim.start()
            
            // Show back card partway through
            cardBack.visibility = View.VISIBLE
            backAnim.start()
            
            isFront = false
        } else {
            frontAnim.setTarget(cardBack)
            backAnim.setTarget(cardFront)
            
            // Start flipping back out
            frontAnim.start()
            
            // Show front card partway through
            cardFront.visibility = View.VISIBLE
            backAnim.start()
            
            isFront = true
        }
    }

    private fun resetCardPosition() {
        // Stop any running animations
        if (frontAnim.isRunning) frontAnim.cancel()
        if (backAnim.isRunning) backAnim.cancel()

        // Reset to initial state (Front showing)
        isFront = true
        
        // Reset properties manually
        cardFront.rotationY = 0f
        cardFront.alpha = 1f
        cardFront.visibility = View.VISIBLE
        
        cardBack.rotationY = -90f // Or whatever the initial hidden state in back_animator is
        cardBack.alpha = 0f
        cardBack.visibility = View.GONE
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
