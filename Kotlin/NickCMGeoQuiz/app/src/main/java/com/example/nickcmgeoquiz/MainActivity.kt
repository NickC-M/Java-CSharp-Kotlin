package com.example.nickcmgeoquiz
//Nicholas Chapman-Miller
//CPT-188-A01S
import android.app.Activity
import android.content.Intent
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nickcmgeoquiz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private val quizViewModel: QuizViewModel by viewModels()

    private var snackbarText: Int = -1

    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if(result.resultCode == Activity.RESULT_OK){
            //change to track cheater per question
            quizViewModel.setCheat(
                result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
            )
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate(Bundle?) called")
        enableEdgeToEdge()
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")


        binding.trueButton.setOnClickListener{ view: View ->
            //what happens when the user clicks true
            checkAnswer(true)
        }

        binding.falseButton.setOnClickListener{ view: View ->
            //what happens when the user clicks false

            checkAnswer(false)

        }
        binding.nextButton.setOnClickListener{

            quizViewModel.moveToNext()
            updateQuestion()
            snackbarText = -1
            binding.hiddenSnackText?.setText(snackbarText)

        }

        binding.previousButton.setOnClickListener {

            quizViewModel.moveToPrevious()
            updateQuestion()
            snackbarText = -1
            binding.hiddenSnackText?.setText(snackbarText)
        }

        binding.questionText.setOnClickListener {

            quizViewModel.moveToNext()
            updateQuestion()

        }

        binding.cheatButton?.setOnClickListener {
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            //val intent = Intent(this, CheatActivity::class.java)
            //can also use .putExtra(name, value) instead of companion func for new intent




            cheatLauncher.launch(intent)
        }
        updateQuestion()
    }

    //overrides
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }


    //functions
    private fun updateQuestion(){
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionText.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer


        val messageResId = if (quizViewModel.currentQuestionAnswerShown) {
            R.string.judgement_toast
        }else if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Snackbar.make(binding.root.rootView, messageResId, Snackbar.LENGTH_SHORT)
            .show()
        snackbarText = messageResId
        binding.hiddenSnackText?.setText(snackbarText)

    }


}