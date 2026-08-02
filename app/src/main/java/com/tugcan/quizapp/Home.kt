package com.tugcan.quizapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.tugcan.quizapp.databinding.ActivityHomeBinding


class Home : AppCompatActivity() {
    private lateinit var binding:ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


enableEdgeToEdge()


        binding.playButton.setOnClickListener {
            val intent = Intent(this,QuizScreen::class.java)
            startActivity(intent)
        }

    }
}