package com.example.nickcmgeoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nickcmgeoquiz.databinding.ActivityCheatBinding
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlin.getValue

private const val EXTRA_ANSWER_IS_TRUE = "nickcmgeoquiz.answer.answer_is_true"

const val EXTRA_ANSWER_SHOWN = "nickcmgeoquiz.answer.answer_shown"


private var answerIsTrue = false
private var isAnswerShown = false
class CheatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isAnswerShown = savedInstanceState?.getBoolean(EXTRA_ANSWER_SHOWN, false) ?: false


        enableEdgeToEdge()
        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if(isAnswerShown){
            val answerText = when{
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }

            binding.answerTextView.setText(answerText)
            setAnswerShownResult(true)
        }





    binding.showAnswerButton.setOnClickListener {
        val answerText = when{
            answerIsTrue -> R.string.true_button
            else -> R.string.false_button
        }

        isAnswerShown = true

        //could also do an if statement instead of when
        binding.answerTextView.setText(answerText)
        //settings teh is answer shown to true if they cheated
        setAnswerShownResult(true)
    }

    }

    private fun setAnswerShownResult(isAnswerShown: Boolean){

        val data = intent.apply{
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)

        }



        setResult(Activity.RESULT_OK, data)

    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(EXTRA_ANSWER_SHOWN, isAnswerShown)
    }




    //companion = static method that you can call from other classes
    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply()
            {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }

    }


}