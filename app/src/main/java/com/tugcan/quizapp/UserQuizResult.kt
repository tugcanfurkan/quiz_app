package com.tugcan.quizapp

import android.os.Parcel
import android.os.Parcelable

data class UserQuizResult(val questionNum: Int, val question:String, val answer:String, val userAnswer:String):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.readString()?: ""
    )



    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(questionNum)
        parcel.writeString(question)
        parcel.writeString(answer)
        parcel.writeString(userAnswer)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserQuizResult> {
        override fun createFromParcel(parcel: Parcel): UserQuizResult {
            return UserQuizResult(parcel)
        }

        override fun newArray(size: Int): Array<UserQuizResult?> {
            return arrayOfNulls(size)
        }
    }
}