package com.tugcan.quizapp

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.tugcan.quizapp.databinding.ActivityQuizScreenBinding

class QuizScreen : AppCompatActivity() {

    private val userQuizResultList = ArrayList<UserQuizResult>()

    var spaceAnswerAmount: Int = 0
    private lateinit var binding: ActivityQuizScreenBinding


    private lateinit var db: QuizAppDatabase


    var questionOrder: Int = -1
    var questionText: Int = 0


    var trueAnswerAmount: Int = 0
    var falseAnswerAmount: Int = 0

    var trueAnswer: String = "trueAnswer"

    var questionsList: List<Question> = emptyList()

    private fun pullTheQuestions() {

        db = QuizAppDatabase(this)

        questionsList = db.getRandomQuestions(10)

    }

    private fun loadNewQuestion() {


        //The skip button is disabled when transitioning to the 10th question.
        if (questionOrder == 8) {

            binding.passBtn.isEnabled = false
            binding.passBtn.setBackgroundColor(Color.rgb(128, 128, 128))
            binding.passBtn.setTextColor(Color.rgb(230, 230, 230))

        }
        if (questionOrder == 9) {
            finishTheQuiz()
        }
        //Questions beyond the 10th cannot be loaded.
        if (questionOrder < 9) {


            questionOrder += 1
            questionText += 1

            binding.questionNumberTextView.setText(questionText.toString() + "/10")

            binding.questionTextView.setText(questionsList[questionOrder].question)
            binding.optABtn.setText(questionsList[questionOrder].OPTA)
            binding.optBBtn.setText(questionsList[questionOrder].OPTB)
            binding.optCBtn.setText(questionsList[questionOrder].OPTC)
            binding.optDBtn.setText(questionsList[questionOrder].OPTD)

            trueAnswer = questionsList[questionOrder].answer


        }


    }


    private fun finishTheQuiz() {

        val intent = Intent(this, QuizResultScreen::class.java)
        intent.putParcelableArrayListExtra("userQuizResultList", userQuizResultList)
        intent.putExtra("trueAnswerAmount", trueAnswerAmount.toString())
        intent.putExtra("falseAnswerAmount", falseAnswerAmount.toString())
        intent.putExtra("spaceAnswerAmount", spaceAnswerAmount.toString())
        startActivity(intent)
        finish()
    }

    private fun checkTheAnswer(userAnswer: String) {
        if (trueAnswer == userAnswer) {
            trueAnswerAmount++
            showAnswerResultDialogBox(userAnswer)

        } else if (userAnswer == "space") {
            spaceAnswerAmount++
        } else if (trueAnswer != userAnswer) {
            showAnswerResultDialogBox(userAnswer)
            falseAnswerAmount++
        }


        saveTheUserAnswers(userAnswer)
    }

    private fun saveTheUserAnswers(userAnswer: String) {


        userQuizResultList.add(
            UserQuizResult(
                questionText,
                questionsList[questionOrder].question,
                questionsList[questionOrder].answer,
                userAnswer,
            )
        )


    }

    private fun showAnswerResultDialogBox(userAnswer: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.answer_result_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val answerResultDialogImage: ImageView = dialog.findViewById(R.id.answerResultDialogImage)
        val answerResultDialog: TextView = dialog.findViewById(R.id.resultDialogTextView)
        val questionDialogText: TextView = dialog.findViewById(R.id.questionTextView)
        val trueAnswerDialogText: TextView = dialog.findViewById(R.id.trueAnswerTextView)
        val userAnswerDialogText: TextView = dialog.findViewById(R.id.userAnswerTextView)
        val btnNextQuestion: Button = dialog.findViewById(R.id.nextQuestionBtn)

        questionDialogText.setText(questionsList[questionOrder].question)
        trueAnswerDialogText.setText(trueAnswer)
        userAnswerDialogText.setText(userAnswer)
        if (trueAnswer == userAnswer) {
            answerResultDialog.setText("True")
            answerResultDialogImage.setImageResource(R.drawable.baseline_check_circle_24)
        } else {
            answerResultDialog.setText("False")
            answerResultDialogImage.setImageResource(R.drawable.baseline_highlight_off_24)
        }

        btnNextQuestion.text = if (questionOrder==9) "Finish" else "Next"

        btnNextQuestion.setOnClickListener {
            loadNewQuestion()
            dialog.dismiss()
        }

        dialog.show()

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun exitConfirmationDialog() {
        val exitdialog = AlertDialog.Builder(this)
        exitdialog.setMessage("Are you sure you want to leave?")
        exitdialog.setTitle("Whoops")
        exitdialog.setCancelable(false)
        exitdialog.setPositiveButton("yes") { _, _ ->


            if (questionOrder == 9) {
                saveTheUserAnswers("empty")
            }//If the last answer is blank, save it for the quiz result so the user can view it.
            finishTheQuiz()
        }
        exitdialog.setNegativeButton("no") { _, _ ->

        }

        exitdialog.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_screen)
        binding = ActivityQuizScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        pullTheQuestions()
        loadNewQuestion()


        binding.optABtn.setOnClickListener {
            checkTheAnswer(binding.optABtn.text.toString())
        }

        binding.optBBtn.setOnClickListener {
            checkTheAnswer(binding.optBBtn.text.toString())
        }

        binding.optCBtn.setOnClickListener {
            checkTheAnswer(binding.optCBtn.text.toString())
        }

        binding.optDBtn.setOnClickListener {
            checkTheAnswer(binding.optDBtn.text.toString())
        }

        binding.passBtn.setOnClickListener {
            checkTheAnswer("space")
            loadNewQuestion()
        }
        binding.exitBtn.setOnClickListener {
            exitConfirmationDialog()
        }

    }


}