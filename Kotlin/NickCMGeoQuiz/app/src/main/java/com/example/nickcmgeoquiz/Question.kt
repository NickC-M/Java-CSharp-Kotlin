package com.example.nickcmgeoquiz

import androidx.annotation.StringRes



data class Question(@StringRes val textResId: Int, val answer: Boolean, var answerShown: Boolean) {

}