package com.finnvocab.app

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.migration.Migration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Word::class], version = 2, exportSchema = false)
abstract class WordDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao

    companion object {
        @Volatile
        private var INSTANCE: WordDatabase? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE words ADD COLUMN sentenceEnglish TEXT NOT NULL DEFAULT ''")
            }
        }

        fun getDatabase(context: Context, scope: CoroutineScope): WordDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordDatabase::class.java,
                    "word_database"
                )
                .addMigrations(MIGRATION_1_2)
                .addCallback(WordDatabaseCallback(scope))
                .build()
                INSTANCE = instance
                instance
            }
        }

        private class WordDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch {
                        populateDatabase(database.wordDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(wordDao: WordDao) {
            // Pre-populate with sample words
            wordDao.insertWord(Word(finnish = "Hei", english = "Hello", category = "Greetings", sentence = "Hei! Mitä kuuluu?", sentenceEnglish = "Hello! How are you?"))
            wordDao.insertWord(Word(finnish = "Kiitos", english = "Thank you", category = "Greetings", sentence = "Kiitos paljon!", sentenceEnglish = "Thank you very much!"))
            wordDao.insertWord(Word(finnish = "Kahvi", english = "Coffee", category = "Food", sentence = "Haluaisin kupillisen kahvia.", sentenceEnglish = "I would like a cup of coffee."))
            wordDao.insertWord(Word(finnish = "Vesi", english = "Water", category = "Food", sentence = "Saisinko lasin vettä?", sentenceEnglish = "Could I have a glass of water?"))
            wordDao.insertWord(Word(finnish = "Yksi", english = "One", category = "Numbers", sentence = "Minulla on yksi koira.", sentenceEnglish = "I have one dog."))
            wordDao.insertWord(Word(finnish = "Kaksi", english = "Two", category = "Numbers", sentence = "Tarvitsen kaksi lippua.", sentenceEnglish = "I need two tickets."))
        }
    }
}
