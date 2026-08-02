package com.tugcan.quizapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class QuizAppDatabase(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "quizDatabase"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allquestions"
        private const val COLUMN_ID = "id"
        private const val COLUMN_QUESTION = "question"
        private const val COLUMN_OPTA = "opta"
        private const val COLUMN_OPTB = "optb"
        private const val COLUMN_OPTC = "optc"
        private const val COLUMN_OPTD = "optd"
        private const val COLUMN_ANSWER = "answer"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery =
            "CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,$COLUMN_QUESTION TEXT,$COLUMN_OPTA TEXT,$COLUMN_OPTB TEXT,$COLUMN_OPTC TEXT,$COLUMN_OPTD TEXT,$COLUMN_ANSWER TEXT)"
        db?.execSQL(createTableQuery)

        seedQuestions(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }


    private fun seedQuestions(db: SQLiteDatabase?) {
        db?.beginTransaction()
        try {
            for (q in getDefaultQuestions()) {
                val values = ContentValues().apply {
                    put(COLUMN_QUESTION, q.question)
                    put(COLUMN_OPTA, q.OPTA)
                    put(COLUMN_OPTB, q.OPTB)
                    put(COLUMN_OPTC, q.OPTC)
                    put(COLUMN_OPTD, q.OPTD)
                    put(COLUMN_ANSWER, q.answer)
                }
                db?.insert(TABLE_NAME, null, values)
            }
            db?.setTransactionSuccessful()
        } finally {
            db?.endTransaction()
        }
    }

    private fun getDefaultQuestions(): List<Question> {
        return listOf(

            Question(question = "What is the chemical symbol for water?", OPTA = "CO2", OPTB = "H2O", OPTC = "O2", OPTD = "NaCl", answer = "H2O"),
            Question(question = "What is the square root of 64?", OPTA = "6", OPTB = "7", OPTC = "8", OPTD = "9", answer = "8"),
            Question(question = "How many degrees are in a full circle?", OPTA = "180", OPTB = "270", OPTC = "360", OPTD = "420", answer = "360"),
            Question(question = "What is the result of 2 to the power of 8 (2^8)?", OPTA = "128", OPTB = "256", OPTC = "512", OPTD = "1024", answer = "256"),
            Question(question = "Which base-2 number system is natively used by modern computers?", OPTA = "Decimal", OPTB = "Hexadecimal", OPTC = "Binary", OPTD = "Octal", answer = "Binary"),
            Question(question = "How many bones are in an adult human body?", OPTA = "186", OPTB = "206", OPTC = "226", OPTD = "256", answer = "206"),
            Question(question = "What is the freezing point of water in Fahrenheit?", OPTA = "0°F", OPTB = "32°F", OPTC = "100°F", OPTD = "212°F", answer = "32°F"),
            Question(question = "Which binary number represents the decimal value 5?", OPTA = "100", OPTB = "101", OPTC = "110", OPTD = "111", answer = "101"),
            Question(question = "How many minutes are in a full day (24 hours)?", OPTA = "1200", OPTB = "1440", OPTC = "1600", OPTD = "1800", answer = "1440"),
            Question(question = "How many bits are in a single byte?", OPTA = "4", OPTB = "8", OPTC = "16", OPTD = "32", answer = "8"),
            Question(question = "What is the largest organ in the human body?", OPTA = "Liver", OPTB = "Heart", OPTC = "Skin", OPTD = "Lungs", answer = "Skin"),
            )
    }

    fun getRandomQuestions(questionAmount: Int): List<Question> {
        val questionsList = mutableListOf<Question>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME ORDER BY RANDOM() LIMIT $questionAmount"
        val cursor = db.rawQuery(query, null)


        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val question = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QUESTION))
                val optA = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OPTA))
                val optB = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OPTB))
                val optC = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OPTC))
                val optD = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OPTD))
                val answer = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ANSWER))

                questionsList.add(Question(id, question, optA, optB, optC, optD, answer))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return questionsList
    }


}