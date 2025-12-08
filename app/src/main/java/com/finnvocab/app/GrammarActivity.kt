package com.finnvocab.app

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView

class GrammarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grammar)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Grammar"

        val container = findViewById<LinearLayout>(R.id.grammarContentContainer)
        val topicId = intent.getIntExtra("GRAMMAR_TOPIC_ID", -1)

        displayGrammarContent(topicId, container)
    }

    private fun displayGrammarContent(topicId: Int, container: LinearLayout) {
        // Clear previous content
        container.removeAllViews()

        val titleView = TextView(this).apply {
            textSize = 24f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(resources.getColor(R.color.primary, null))
            setPadding(0, 0, 0, 16)
        }

        val contentView = TextView(this).apply {
            textSize = 16f
            setTextColor(resources.getColor(R.color.textPrimary, null))
            setLineSpacing(0f, 1.2f)
        }

        when (topicId) {
            R.id.nav_pronouns -> {
                titleView.text = "Persoonapronominit ja 'olla' verbi"
                contentView.text = """
                    Personal Pronouns:
                    Minä - I
                    Sinä - You (singular)
                    Hän - He/She
                    Me - We
                    Te - You (plural/formal)
                    He - They

                    Verb 'olla' (to be) - Positive:
                    (Minä) olen - I am
                    (Sinä) olet - You are
                    Hän on - He/She is
                    (Me) olemme - We are
                    (Te) olette - You are
                    He ovat - They are

                    Verb 'olla' (to be) - Negative:
                    (Minä) en ole - I am not
                    (Sinä) et ole - You are not
                    Hän ei ole - He/She is not
                    (Me) emme ole - We are not
                    (Te) ette ole - You are not
                    He eivät ole - They are not
                """.trimIndent()
            }
            R.id.nav_genitive -> {
                titleView.text = "Genetiivi (Genitive Case)"
                contentView.text = """
                    The Genitive case indicates possession (like 's in English).
                    Ending: -n

                    Examples:
                    Talo (House) -> Talon (House's / Of the house)
                    Auto (Car) -> Auton (Car's / Of the car)
                    Kuka (Who) -> Kenen (Whose)

                    Used with postpositions:
                    Pöydän alla (Under the table)
                    Talon edessä (In front of the house)

                    Used to express necessity (Minun täytyy...):
                    Minun täytyy mennä. (I have to go.)
                """.trimIndent()
            }
            R.id.nav_partitive -> {
                titleView.text = "Partitiivi (Partitive Case)"
                contentView.text = """
                    The Partitive case expresses indefinite amounts, ongoing actions, or negative sentences.
                    Endings: -a, -ä, -ta, -tä, -tta, -ttä

                    Uses:
                    1. After numbers (except 1):
                       Yksi omena (One apple - Nominative)
                       Kaksi omenaa (Two apples - Partitive)
                    
                    2. Uncountable nouns / Mass nouns:
                       Juon kahvia. (I drink coffee.)
                       Syön leipää. (I eat bread.)

                    3. Negative sentences:
                       Minulla ei ole autoa. (I don't have a car.)
                       En syö lihaa. (I don't eat meat.)

                    4. Ongoing action (Irresultative):
                       Luen kirjaa. (I am reading a book.)
                """.trimIndent()
            }
            R.id.nav_kpt -> {
                titleView.text = "K-P-T Vaihtelu (Consonant Gradation)"
                contentView.text = """
                    Consonants k, p, and t change in certain cases (like Genitive or when adding personal endings).
                    
                    Strong Grade -> Weak Grade:
                    
                    kk -> k  (kukka -> kukan)
                    pp -> p  (kauppa -> kaupan)
                    tt -> t  (tyttö -> tytön)
                    
                    k -> -   (jalka -> jalan) *disappears*
                    p -> v   (leipä -> leivän)
                    t -> d   (pöytä -> pöydän)
                    
                    nk -> ng (Helsinki -> Helsingin)
                    mp -> mm (kampa -> kamman)
                    lt -> ll (ilto -> illan)
                    rt -> rr (parta -> parran)
                    nt -> nn (antaa -> annan)

                    Note: Some words do not change (e.g., auto -> auton).
                """.trimIndent()
            }
            R.id.nav_verb_types -> {
                titleView.text = "Verbityypit (Verb Types)"
                contentView.text = """
                    There are 6 verb types in Finnish.
                    
                    Type 1 (-Va / -Vä):
                    Puhua (to speak)
                    + Minä puhun (I speak)
                    - Minä en puhu (I don't speak)
                    
                    Type 2 (-da / -dä):
                    Syödä (to eat)
                    + Minä syön (I eat)
                    - Minä en syö (I don't eat)
                    
                    Type 3 (-la/lä, -na/nä, -ra/rä, -sta/stä):
                    Tulla (to come)
                    + Minä tulen (I come)
                    - Minä en tule (I don't come)
                    
                    Type 4 (-ta / -tä):
                    Haluta (to want)
                    + Minä haluan (I want)
                    - Minä en halua (I don't want)
                    
                    Type 5 (-ita / -itä):
                    Tarvita (to need)
                    + Minä tarvitsen (I need)
                    - Minä en tarvitse (I don't need)
                    
                    Type 6 (-eta / -etä):
                    Vanheta (to grow old)
                    + Minä vanhenen (I grow old)
                    - Minä en vanhene (I don't grow old)
                """.trimIndent()
            }
            R.id.nav_question_words -> {
                titleView.text = "Kysymyssanat (Question Words)"
                contentView.text = """
                    Mitä? - What?
                    Kuka? - Who?
                    Missä? - Where (at/in)?
                    Mistä? - Where (from)?
                    Mihin? - Where (to)?
                    Milloin? - When?
                    Miksi? - Why?
                    Miten? - How?
                    Kumpi? - Which (of two)?
                    Paljonko? - How much?
                    
                    Examples:
                    Mitä tämä on? (What is this?)
                    Kuka sinä olet? (Who are you?)
                    Missä asut? (Where do you live?)
                    Mihin menet? (Where are you going?)
                """.trimIndent()
            }
            R.id.nav_cases -> {
                titleView.text = "Finnish Cases Overview"
                contentView.text = """
                    Finnish has 15 grammatical cases. Here are the most common ones:

                    1. Nominative (Basic form):
                       Talo (House)
                    
                    2. Genitive (Possession 'of'):
                       Talon (Of the house)
                    
                    3. Partitive (Indefinite amount/part):
                       Taloa (Some house / part of house)
                    
                    Internal Locative Cases (In/Into/Out of):
                    4. Inessive (In): Talossa (In the house)
                    5. Elative (From inside): Talosta (From the house)
                    6. Illative (Into): Taloon (Into the house)
                    
                    External Locative Cases (On/Onto/Off of):
                    7. Adessive (On/At): Talolla (At the house)
                    8. Ablative (From on/at): Talolta (From the house)
                    9. Allative (To/Onto): Talolle (To the house)
                """.trimIndent()
            }
            else -> {
                titleView.text = "Grammar Guide"
                contentView.text = "Select a topic from the menu to learn more."
            }
        }

        container.addView(titleView)
        container.addView(contentView)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
