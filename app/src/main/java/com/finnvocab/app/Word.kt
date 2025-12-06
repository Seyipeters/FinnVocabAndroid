package com.finnvocab.app

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val finnish: String,
    val english: String,
    val category: String,
    val sentence: String = "",
    val sentenceEnglish: String = "",
    val mastery: Int = 0,
    val isFavorite: Boolean = false
)
