package com.finnvocab.app

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class QuizActivity : AppCompatActivity() {

    private lateinit var database: WordDatabase
    private lateinit var wordDao: WordDao

    // Data
    private var allWords: List<Word> = emptyList()
    private var currentQuestionIndex = 0
    private var totalQuestions = 10
    private var score = 0
    private lateinit var currentWord: Word
    private var correctAnswerIndex = 0

    // UI Elements
    private lateinit var tvProgress: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvQuestionWord: TextView
    private lateinit var optionsContainer: LinearLayout
    private lateinit var feedbackPopup: View
    private lateinit var ivFeedbackIcon: ImageView
    private lateinit var tvFeedbackText: TextView
    
    private val optionButtons = mutableListOf<Button>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Quiz"

        database = WordDatabase.getDatabase(this, lifecycleScope)
        wordDao = database.wordDao()

        initializeViews()
        loadWords()
    }

    private fun initializeViews() {
        tvProgress = findViewById(R.id.tvProgress)
        progressBar = findViewById(R.id.progressBar)
        tvQuestionWord = findViewById(R.id.tvQuestionWord)
        optionsContainer = findViewById(R.id.optionsContainer)
        feedbackPopup = findViewById(R.id.feedbackPopup)
        ivFeedbackIcon = findViewById(R.id.ivFeedbackIcon)
        tvFeedbackText = findViewById(R.id.tvFeedbackText)

        optionButtons.add(findViewById(R.id.btnOption1))
        optionButtons.add(findViewById(R.id.btnOption2))
        optionButtons.add(findViewById(R.id.btnOption3))
        optionButtons.add(findViewById(R.id.btnOption4))

        for (i in 0 until optionButtons.size) {
            optionButtons[i].setOnClickListener { checkAnswer(i) }
        }
    }

    private fun loadWords() {
        lifecycleScope.launch {
            val words = wordDao.getAllWords().first()
            if (words.size >= 4) {
                allWords = words.shuffled()
                startQuiz()
            } else {
                tvQuestionWord.text = "Not enough words to start quiz."
                optionsContainer.visibility = View.GONE
            }
        }
    }

    private fun startQuiz() {
        currentQuestionIndex = 0
        score = 0
        totalQuestions = minOf(10, allWords.size)
        progressBar.max = totalQuestions
        showNextQuestion()
    }

    private fun showNextQuestion() {
        if (currentQuestionIndex >= totalQuestions) {
            showQuizResult()
            return
        }

        // Update Progress
        tvProgress.text = "Question ${currentQuestionIndex + 1}/$totalQuestions"
        progressBar.progress = currentQuestionIndex + 1

        // Pick current word (correct answer)
        currentWord = allWords[currentQuestionIndex]
        tvQuestionWord.text = currentWord.finnish

        // Generate options (1 correct + 3 wrong)
        val wrongAnswers = allWords.filter { it.id != currentWord.id }.shuffled().take(3)
        val options = (wrongAnswers + currentWord).shuffled()

        // Find which index is the correct answer
        correctAnswerIndex = options.indexOfFirst { it.id == currentWord.id }

        // Set text for buttons
        for (i in 0 until 4) {
            optionButtons[i].text = options[i].english
            optionButtons[i].isEnabled = true
            optionButtons[i].backgroundTintList = getColorStateList(R.color.white)
            optionButtons[i].setTextColor(getColor(R.color.textPrimary))
        }
    }

    private fun checkAnswer(selectedIndex: Int) {
        // Disable buttons to prevent double clicks
        optionButtons.forEach { it.isEnabled = false }

        val isCorrect = selectedIndex == correctAnswerIndex

        if (isCorrect) {
            score++
            showFeedback(true)
        } else {
            showFeedback(false)
        }

        // Wait then move to next question
        lifecycleScope.launch {
            delay(2000) // Keep popup visible for 2 seconds
            hideFeedback()
            delay(300) // Wait for fade out
            currentQuestionIndex++
            showNextQuestion()
        }
    }

    private fun showFeedback(isCorrect: Boolean) {
        // Reset state for animation
        feedbackPopup.visibility = View.VISIBLE
        feedbackPopup.alpha = 0f
        feedbackPopup.scaleX = 0.5f
        feedbackPopup.scaleY = 0.5f
        
        if (isCorrect) {
            ivFeedbackIcon.setImageResource(R.drawable.ic_check)
            ivFeedbackIcon.setColorFilter(getColor(R.color.success)) // Green icon
            tvFeedbackText.text = "Correct!"
            tvFeedbackText.setTextColor(getColor(R.color.success))
        } else {
            ivFeedbackIcon.setImageResource(R.drawable.ic_close)
            ivFeedbackIcon.setColorFilter(getColor(R.color.error_transparent)) // Red icon (using error color)
            // If you don't have a solid error color, use accent or define one. 
            // Let's use Color.RED or a specific color resource.
            // Assuming error_transparent is reddish, but better to use a solid color for the icon.
            ivFeedbackIcon.setColorFilter(Color.RED) 
            
            tvFeedbackText.text = "Wrong!\nIt was: ${currentWord.english}"
            tvFeedbackText.setTextColor(Color.RED)
        }

        // Pop-up animation
        feedbackPopup.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(400)
            .setInterpolator(OvershootInterpolator())
            .start()
    }

    private fun hideFeedback() {
        feedbackPopup.animate()
            .alpha(0f)
            .scaleX(0.5f)
            .scaleY(0.5f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    feedbackPopup.visibility = View.GONE
                }
            })
            .start()
    }

    private fun showQuizResult() {
        tvProgress.text = "Quiz Completed!"
        progressBar.progress = totalQuestions
        tvQuestionWord.text = "Score: $score / $totalQuestions"
        
        optionsContainer.visibility = View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
