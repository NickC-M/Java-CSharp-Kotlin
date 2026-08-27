package com.example.nickcmgeoquiz

import androidx.lifecycle.SavedStateHandle
import org.junit.Assert
import org.junit.Test

class QuizViewModelTest {
    @Test
    fun providesExpectedQuestionText() {
        val savedStateHandle = SavedStateHandle()
        val quizViewModel = QuizViewModel(savedStateHandle)
        Assert.assertEquals(R.string.question_australia, quizViewModel.currentQuestionText)
    }



    @Test
    fun wrapsAroundQuestionBank() {
        val savedStateHandle = SavedStateHandle(mapOf(CURRENT_INDEX_KEY to 5))
        val quizViewModel = QuizViewModel(savedStateHandle)
        Assert.assertEquals(R.string.question_asia, quizViewModel.currentQuestionText)
        quizViewModel.moveToNext()
        Assert.assertEquals(R.string.question_australia, quizViewModel.currentQuestionText)
    }


    @Test
    fun tracksCheaterStatusPerQ() {
        val savedStateHandle = SavedStateHandle(mapOf(CURRENT_INDEX_KEY to 1))
        val quizViewModel = QuizViewModel(savedStateHandle)
        quizViewModel.setCheat(true)
        Assert.assertEquals("true", quizViewModel.currentQuestionAnswerShown.toString().lowercase())
        quizViewModel.moveToNext()
        Assert.assertEquals(
            "false",
            quizViewModel.currentQuestionAnswerShown.toString().lowercase()
        )
        quizViewModel.moveToPrevious()
        Assert.assertEquals("true", quizViewModel.currentQuestionAnswerShown.toString().lowercase())

    }
}