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
            // Delete all content
            wordDao.deleteAllWords()

            // Greetings
            wordDao.insertWord(Word(finnish = "Hei", english = "Hello", category = "Greetings", sentence = "Hei! Mitä kuuluu?", sentenceEnglish = "Hello! How are you?"))
            wordDao.insertWord(Word(finnish = "Hyvää huomenta", english = "Good morning", category = "Greetings", sentence = "Hyvää huomenta kaikille.", sentenceEnglish = "Good morning everyone."))
            wordDao.insertWord(Word(finnish = "Hyvää päivää", english = "Good afternoon", category = "Greetings", sentence = "Hyvää päivää, onko tohtori paikalla?", sentenceEnglish = "Good afternoon, is the doctor in?"))
            wordDao.insertWord(Word(finnish = "Hyvää iltaa", english = "Good evening", category = "Greetings", sentence = "Hyvää iltaa, pöytä kahdelle, kiitos.", sentenceEnglish = "Good evening, a table for two, please."))
            wordDao.insertWord(Word(finnish = "Hyvää yötä", english = "Good night", category = "Greetings", sentence = "Hyvää yötä ja kauniita unia.", sentenceEnglish = "Good night and sweet dreams."))
            wordDao.insertWord(Word(finnish = "Näkemiin", english = "Goodbye", category = "Greetings", sentence = "Näkemiin! Nähdään huomenna.", sentenceEnglish = "Goodbye! See you tomorrow."))
            wordDao.insertWord(Word(finnish = "Kiitos", english = "Thank you", category = "Greetings", sentence = "Kiitos avustasi.", sentenceEnglish = "Thank you for your help."))
            wordDao.insertWord(Word(finnish = "Ole hyvä", english = "You're welcome / Here you are", category = "Greetings", sentence = "Ole hyvä, tässä on kuittisi.", sentenceEnglish = "Here you are, here is your receipt."))
            wordDao.insertWord(Word(finnish = "Anteeksi", english = "Sorry / Excuse me", category = "Greetings", sentence = "Anteeksi, en ymmärtänyt.", sentenceEnglish = "Sorry, I didn't understand."))
            wordDao.insertWord(Word(finnish = "Kyllä", english = "Yes", category = "Common Words", sentence = "Kyllä, haluaisin kahvia.", sentenceEnglish = "Yes, I would like coffee."))
            wordDao.insertWord(Word(finnish = "Ei", english = "No", category = "Common Words", sentence = "Ei kiitos, olen täynnä.", sentenceEnglish = "No thank you, I am full."))

            // Numbers
            wordDao.insertWord(Word(finnish = "Yksi", english = "One", category = "Numbers", sentence = "Minulla on yksi kissa.", sentenceEnglish = "I have one cat."))
            wordDao.insertWord(Word(finnish = "Kaksi", english = "Two", category = "Numbers", sentence = "Haluaisin kaksi lippua.", sentenceEnglish = "I would like two tickets."))
            wordDao.insertWord(Word(finnish = "Kolme", english = "Three", category = "Numbers", sentence = "Kello on kolme.", sentenceEnglish = "It is three o'clock."))
            wordDao.insertWord(Word(finnish = "Neljä", english = "Four", category = "Numbers", sentence = "Meitä on neljä.", sentenceEnglish = "There are four of us."))
            wordDao.insertWord(Word(finnish = "Viisi", english = "Five", category = "Numbers", sentence = "Viisi euroa, kiitos.", sentenceEnglish = "Five euros, please."))
            wordDao.insertWord(Word(finnish = "Kuusi", english = "Six", category = "Numbers", sentence = "Hän on kuusi vuotta vanha.", sentenceEnglish = "He is six years old."))
            wordDao.insertWord(Word(finnish = "Seitsemän", english = "Seven", category = "Numbers", sentence = "Kauppa aukeaa seitsemältä.", sentenceEnglish = "The shop opens at seven."))
            wordDao.insertWord(Word(finnish = "Kahdeksan", english = "Eight", category = "Numbers", sentence = "Nukuin kahdeksan tuntia.", sentenceEnglish = "I slept eight hours."))
            wordDao.insertWord(Word(finnish = "Yhdeksän", english = "Nine", category = "Numbers", sentence = "Bussi numero yhdeksän.", sentenceEnglish = "Bus number nine."))
            wordDao.insertWord(Word(finnish = "Kymmenen", english = "Ten", category = "Numbers", sentence = "Minulla on kymmenen sormea.", sentenceEnglish = "I have ten fingers."))

            // Food & Drink
            wordDao.insertWord(Word(finnish = "Kahvi", english = "Coffee", category = "Food & Drink", sentence = "Juon kahvia aamulla.", sentenceEnglish = "I drink coffee in the morning."))
            wordDao.insertWord(Word(finnish = "Tee", english = "Tea", category = "Food & Drink", sentence = "Haluatko teetä?", sentenceEnglish = "Do you want tea?"))
            wordDao.insertWord(Word(finnish = "Vesi", english = "Water", category = "Food & Drink", sentence = "Vesi on kylmää.", sentenceEnglish = "The water is cold."))
            wordDao.insertWord(Word(finnish = "Maito", english = "Milk", category = "Food & Drink", sentence = "Lasi maitoa, kiitos.", sentenceEnglish = "A glass of milk, please."))
            wordDao.insertWord(Word(finnish = "Leipä", english = "Bread", category = "Food & Drink", sentence = "Tämä leipä on tuoretta.", sentenceEnglish = "This bread is fresh."))
            wordDao.insertWord(Word(finnish = "Voi", english = "Butter", category = "Food & Drink", sentence = "Voita leivän päälle.", sentenceEnglish = "Butter on the bread."))
            wordDao.insertWord(Word(finnish = "Juusto", english = "Cheese", category = "Food & Drink", sentence = "Pidän juustosta.", sentenceEnglish = "I like cheese."))
            wordDao.insertWord(Word(finnish = "Omena", english = "Apple", category = "Food & Drink", sentence = "Syön omenan päivässä.", sentenceEnglish = "I eat an apple a day."))
            wordDao.insertWord(Word(finnish = "Banaani", english = "Banana", category = "Food & Drink", sentence = "Banaani on keltainen.", sentenceEnglish = "The banana is yellow."))
            wordDao.insertWord(Word(finnish = "Ravintola", english = "Restaurant", category = "Food & Drink", sentence = "Menemme ravintolaan.", sentenceEnglish = "We are going to a restaurant."))

            // Family
            wordDao.insertWord(Word(finnish = "Perhe", english = "Family", category = "Family", sentence = "Minulla on iso perhe.", sentenceEnglish = "I have a big family."))
            wordDao.insertWord(Word(finnish = "Äiti", english = "Mother", category = "Family", sentence = "Äiti on kotona.", sentenceEnglish = "Mother is at home."))
            wordDao.insertWord(Word(finnish = "Isä", english = "Father", category = "Family", sentence = "Isä on töissä.", sentenceEnglish = "Father is at work."))
            wordDao.insertWord(Word(finnish = "Sisko", english = "Sister", category = "Family", sentence = "Hän on minun siskoni.", sentenceEnglish = "She is my sister."))
            wordDao.insertWord(Word(finnish = "Veli", english = "Brother", category = "Family", sentence = "Minulla on kaksi veljeä.", sentenceEnglish = "I have two brothers."))
            wordDao.insertWord(Word(finnish = "Tytär", english = "Daughter", category = "Family", sentence = "Heidän tyttärensä on koulussa.", sentenceEnglish = "Their daughter is at school."))
            wordDao.insertWord(Word(finnish = "Poika", english = "Son", category = "Family", sentence = "Poika pelaa jalkapalloa.", sentenceEnglish = "The son is playing football."))
            wordDao.insertWord(Word(finnish = "Isoäiti", english = "Grandmother", category = "Family", sentence = "Isoäiti leipoo pullaa.", sentenceEnglish = "Grandmother is baking buns."))
            wordDao.insertWord(Word(finnish = "Isoisä", english = "Grandfather", category = "Family", sentence = "Isoisä lukee lehteä.", sentenceEnglish = "Grandfather is reading the newspaper."))

            // Places & Travel
            wordDao.insertWord(Word(finnish = "Koti", english = "Home", category = "Places", sentence = "Menen kotiin.", sentenceEnglish = "I am going home."))
            wordDao.insertWord(Word(finnish = "Koulu", english = "School", category = "Places", sentence = "Lapset ovat koulussa.", sentenceEnglish = "The children are at school."))
            wordDao.insertWord(Word(finnish = "Työ", english = "Work", category = "Places", sentence = "Työ alkaa kahdeksalta.", sentenceEnglish = "Work starts at eight."))
            wordDao.insertWord(Word(finnish = "Kauppa", english = "Shop/Store", category = "Places", sentence = "Käyn kaupassa.", sentenceEnglish = "I am visiting the store."))
            wordDao.insertWord(Word(finnish = "Kirjasto", english = "Library", category = "Places", sentence = "Lainaan kirjan kirjastosta.", sentenceEnglish = "I borrow a book from the library."))
            wordDao.insertWord(Word(finnish = "Suomi", english = "Finland", category = "Places", sentence = "Asun Suomessa.", sentenceEnglish = "I live in Finland."))
            wordDao.insertWord(Word(finnish = "Juna", english = "Train", category = "Travel", sentence = "Juna lähtee raiteelta yksi.", sentenceEnglish = "The train departs from track one."))
            wordDao.insertWord(Word(finnish = "Bussi", english = "Bus", category = "Travel", sentence = "Odotan bussia.", sentenceEnglish = "I am waiting for the bus."))
            wordDao.insertWord(Word(finnish = "Lentokone", english = "Airplane", category = "Travel", sentence = "Lentokone nousee ilmaan.", sentenceEnglish = "The airplane is taking off."))
            wordDao.insertWord(Word(finnish = "Hotelli", english = "Hotel", category = "Travel", sentence = "Yövymme hotellissa.", sentenceEnglish = "We are staying at a hotel."))

            // Verbs
            wordDao.insertWord(Word(finnish = "Olla", english = "To be", category = "Verbs", sentence = "Minä olen iloinen.", sentenceEnglish = "I am happy."))
            wordDao.insertWord(Word(finnish = "Tulla", english = "To come", category = "Verbs", sentence = "Hän tulee tänään.", sentenceEnglish = "He comes today."))
            wordDao.insertWord(Word(finnish = "Mennä", english = "To go", category = "Verbs", sentence = "Me menemme ulos.", sentenceEnglish = "We are going out."))
            wordDao.insertWord(Word(finnish = "Syödä", english = "To eat", category = "Verbs", sentence = "Haluatko syödä?", sentenceEnglish = "Do you want to eat?"))
            wordDao.insertWord(Word(finnish = "Juoda", english = "To drink", category = "Verbs", sentence = "Minä juon vettä.", sentenceEnglish = "I drink water."))
            wordDao.insertWord(Word(finnish = "Nukkua", english = "To sleep", category = "Verbs", sentence = "Vauva nukkuu.", sentenceEnglish = "The baby is sleeping."))
        }
    }
}
