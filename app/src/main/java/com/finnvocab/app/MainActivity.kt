package com.finnvocab.app

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var database: WordDatabase
    private lateinit var wordDao: WordDao
    private lateinit var drawerLayout: DrawerLayout

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
    private lateinit var tvEnglishWord: TextView
    private lateinit var tvEnglishSentence: TextView
    private lateinit var tvBackFinnishSentence: TextView
    private lateinit var btnPrevious: Button
    private lateinit var btnNext: Button
    
    // UI Elements defined here to be accessible in click listeners
    private lateinit var btnCategories: Button
    private lateinit var btnQuiz: Button
    private lateinit var btnAddWord: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Setup Navigation Drawer
        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 
            R.string.navigation_drawer_open, 
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

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

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                // Already on home
            }
            R.id.nav_add_word -> {
                startActivity(Intent(this, AddWordActivity::class.java))
            }
            R.id.nav_quiz -> {
                startActivity(Intent(this, QuizActivity::class.java))
            }
            R.id.nav_categories -> {
                startActivity(Intent(this, CategoriesActivity::class.java))
            }
            R.id.nav_about -> {
                Toast.makeText(this, "FinnVocab Version 1.0", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_pronouns, R.id.nav_genitive, R.id.nav_partitive, 
            R.id.nav_kpt, R.id.nav_verb_types, R.id.nav_question_words, 
            R.id.nav_cases -> {
                val intent = Intent(this, GrammarActivity::class.java)
                intent.putExtra("GRAMMAR_TOPIC_ID", item.itemId)
                startActivity(intent)
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun initializeViews() {
        // Flashcard Elements
        cardContainer = findViewById(R.id.cardContainer)
        cardFront = findViewById(R.id.cardFront)
        cardBack = findViewById(R.id.cardBack)
        tvFinnishWord = findViewById(R.id.tvFinnishWord)
        
        tvEnglishWord = findViewById(R.id.tvEnglishWord)
        tvEnglishSentence = findViewById(R.id.tvEnglishSentence)
        tvBackFinnishSentence = findViewById(R.id.tvBackFinnishSentence)
        
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
        
        // Front: Only Finnish Word
        tvFinnishWord.text = currentWord.finnish
        
        // Back: English Word, Finnish Sentence, English Sentence
        tvEnglishWord.text = currentWord.english
        tvBackFinnishSentence.text = currentWord.sentence
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
