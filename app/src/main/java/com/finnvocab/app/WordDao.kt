package com.finnvocab.app

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Query("SELECT * FROM words ORDER BY category, finnish")
    fun getAllWords(): Flow<List<Word>>

    @Query("SELECT * FROM words WHERE category = :category")
    fun getWordsByCategory(category: String): Flow<List<Word>>

    @Query("SELECT DISTINCT category FROM words ORDER BY category")
    fun getAllCategories(): Flow<List<String>>

    @Query("SELECT * FROM words WHERE id = :wordId")
    suspend fun getWordById(wordId: Int): Word?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: Word)

    @Update
    suspend fun updateWord(word: Word)

    @Delete
    suspend fun deleteWord(word: Word)

    @Query("DELETE FROM words")
    suspend fun deleteAllWords()
}
