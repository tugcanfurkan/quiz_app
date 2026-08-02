package com.tugcan.quizapp

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tugcan.quizapp.databinding.ActivityQuizResultScreenBinding

class QuizResultScreen : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    private lateinit var binding: ActivityQuizResultScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityQuizResultScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        recyclerView = binding.questionAnswersRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)


        val userQuizResultLists: ArrayList<UserQuizResult>? =

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableArrayListExtra("userQuizResultList", UserQuizResult::class.java)

            } else {
                @Suppress("DEPRECATION")
                intent.getParcelableArrayListExtra("userQuizResultList")

            }

        val trueAnswerAmount = intent.getStringExtra("trueAnswerAmount")
        val falseAnswerAmount = intent.getStringExtra("falseAnswerAmount")
        val spaceAnswerAmount = intent.getStringExtra("spaceAnswerAmount")


        binding.tvCorrectCount.setText(trueAnswerAmount)
        binding.tvWrongCount.setText(falseAnswerAmount)
        binding.tvUnansweredCount.setText(spaceAnswerAmount)




        recyclerView.adapter = userQuizResultLists?.let { QuizResultAdapter(it) }

     binding.BtnFinish.setOnClickListener {
         finish()
     }
    }
}
