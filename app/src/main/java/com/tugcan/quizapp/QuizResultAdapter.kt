package com.tugcan.quizapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuizResultAdapter(private val userQuizResultList:ArrayList<UserQuizResult>): RecyclerView.Adapter<QuizResultAdapter.ViewHolderClass>() {

class ViewHolderClass(itemView: View): RecyclerView.ViewHolder(itemView){
    val questionNumber:TextView= itemView.findViewById(R.id.questionItemNumLabel)
    val question:TextView = itemView.findViewById(R.id.questionItemTextView)
    val answer:TextView = itemView.findViewById(R.id.questionAnswerItemText)
    val userAnswer:TextView = itemView.findViewById(R.id.userAnswerItemText)

}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.answer_result_item, parent, false)
        return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
    return userQuizResultList.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = userQuizResultList[position]
        holder.questionNumber.text = currentItem.questionNum.toString()
        holder.question.text = currentItem.question.toString()
        holder.answer.text = currentItem.answer.toString()
        holder.userAnswer.text= currentItem.userAnswer.toString()
    }
}