package com.example.nickcmgeoquiz

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"


const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"

class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    init {

    }
    override fun onCleared() {
        super.onCleared()

    }

    private var questionBank = listOf(

        Question(R.string.question_australia, answer = true, answerShown = false),
        Question(R.string.question_oceans, answer = true, answerShown = false),
        Question(R.string.question_mideast, answer = false, answerShown = false),
        Question(R.string.question_africa, answer = false, answerShown = false),
        Question(R.string.question_americas, answer = true, answerShown = false),
        Question(R.string.question_asia, answer = true, answerShown = false),

        )





    private var currentIndex: Int
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId


    var currentQuestionAnswerShown: Boolean = false
        get() = questionBank[currentIndex].answerShown


    fun setCheat(isCheater: Boolean ){
        questionBank[currentIndex].answerShown = isCheater;
    }


    fun moveToNext(){

        currentIndex = (currentIndex +1) % questionBank.size
    }

    fun moveToPrevious(){

        if(currentIndex == 0){
            currentIndex = questionBank.size -1

        }else {
            currentIndex = (currentIndex - 1) % questionBank.size
        }
    }
}