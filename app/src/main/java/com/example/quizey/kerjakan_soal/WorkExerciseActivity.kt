package com.example.quizey.kerjakan_soal

import ExerciseItemData
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.quizey.R
import com.example.quizey.api_service.ApiService
import com.example.quizey.api_service.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.await

class WorkExerciseActivity : AppCompatActivity() {
    private lateinit var questionTextView: TextView
    private lateinit var nextButton: Button
    private lateinit var exerciseItems: List<ExerciseItemData>
    private var currentQuestionIndex = 0
    private lateinit var optionA: Button
    private lateinit var optionB: Button
    private lateinit var optionC: Button
    private lateinit var optionD: Button
    private lateinit var optionE: Button
    private lateinit var selectedOption: Button
    private lateinit var selectAnswer: String
    private lateinit var email: String
    private lateinit var idExercise: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_exercise)

        email = intent.getStringExtra("EMAIL_KEY").toString()
        idExercise = intent.getStringExtra("EXERCISE_ID").toString()

        questionTextView = findViewById(R.id.questionTextView)
        nextButton = findViewById(R.id.nextButton)
        optionA = findViewById(R.id.optionButton1)
        optionB = findViewById(R.id.optionButton2)
        optionC = findViewById(R.id.optionButton3)
        optionD = findViewById(R.id.optionButton4)
        optionE = findViewById(R.id.optionButton5)

        selectedOption = optionA
        selectAnswer = ""

        val exerciseService = RetrofitClient.retrofit.create(ApiService::class.java)

        lifecycleScope.launch {
            try {
                val response = exerciseService.getExerciseDataSoal(
                    exerciseId = idExercise,
                    userEmail = email
                ).await()

                if (response.message == "allowed to access test") {
                    exerciseItems = response.data
                    displayCurrentQuestion()
                } else {
                    val errorMessage = response.status
                    Log.e("WorkExerciseActivity", "Error: $errorMessage")
                    Toast.makeText(
                        this@WorkExerciseActivity,
                        "Failed to fetch exercise: $errorMessage",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Log.e("WorkExerciseActivity", "An error occurred", e)
                Toast.makeText(this@WorkExerciseActivity, "An error occurred", Toast.LENGTH_SHORT).show()
            }
        }

        nextButton.setOnClickListener {
            if (currentQuestionIndex < exerciseItems.size - 1) {
                currentQuestionIndex++
                displayCurrentQuestion()
            } else {
                val currentQuestion = exerciseItems[currentQuestionIndex]
                val exerciseServices = RetrofitClient.retrofit.create(ApiService::class.java)
                lifecycleScope.launch {
                    try {
                        exerciseServices.postDoneExerciseAnswers(
                            userEmail = email,
                            exerciseId = currentQuestion.exerciseIdFk,
                        )
                        val intent = Intent(this@WorkExerciseActivity, HasilActivity::class.java)
                        intent.putExtra("EMAIL_KEY", email)
                        intent.putExtra("EXERCISE_ID", currentQuestion.exerciseIdFk)
                        startActivity(intent)
                    } catch (e: Exception) {
                        Log.e("WorkExerciseActivity", "Error sending answer", e)
                        Toast.makeText(this@WorkExerciseActivity, "An error", Toast.LENGTH_SHORT).show()
                    }
                }


            }
        }

        optionA.setOnClickListener { handleOptionClick(optionA)
            selectAnswer = "A"}
        optionB.setOnClickListener { handleOptionClick(optionB)
            selectAnswer = "B"}
        optionC.setOnClickListener { handleOptionClick(optionC)
            selectAnswer = "C"}
        optionD.setOnClickListener { handleOptionClick(optionD)
            selectAnswer = "D"}
        optionE.setOnClickListener { handleOptionClick(optionE)
            selectAnswer = "E"}
    }

    private fun displayCurrentQuestion() {
        val currentQuestion = exerciseItems[currentQuestionIndex]
        questionTextView.text = removeHtmlTags(currentQuestion.questionTitle)
        optionA.text = removeHtmlTags(currentQuestion.optionA)
        optionB.text = removeHtmlTags(currentQuestion.optionB)
        optionC.text = removeHtmlTags(currentQuestion.optionC)
        optionD.text = removeHtmlTags(currentQuestion.optionD)
        optionE.text = removeHtmlTags(currentQuestion.optionE)

        resetOptions()
    }

    @SuppressLint("ResourceAsColor")
    private fun handleOptionClick(clickedOption: Button) {
        selectedOption.setBackgroundResource(R.drawable.button_selected_border)
        selectedOption = clickedOption
        selectedOption.setBackgroundResource(R.drawable.button_costume)

        sendAnswerToApi()
    }

    private fun sendAnswerToApi() {
        val currentQuestion = exerciseItems[currentQuestionIndex]
        val exerciseService = RetrofitClient.retrofit.create(ApiService::class.java)
        lifecycleScope.launch {
            try {
                exerciseService.postExerciseAnswers(
                    userEmail = email,
                    exerciseId = currentQuestion.exerciseIdFk,
                    questionIds = listOf(currentQuestion.bankQuestionId),
                    studentAnswers = listOf(selectAnswer)
                )
            } catch (e: Exception) {
                Log.e("WorkExerciseActivity", "Error sending answer", e)
                Toast.makeText(this@WorkExerciseActivity, "An error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun resetOptions() {
        optionA.setBackgroundResource(R.drawable.border_button)
        optionB.setBackgroundResource(R.drawable.border_button)
        optionC.setBackgroundResource(R.drawable.border_button)
        optionD.setBackgroundResource(R.drawable.border_button)
        optionE.setBackgroundResource(R.drawable.border_button)
    }

    private fun removeHtmlTags(input: String): String {
        return input.replace(Regex("<.*?>"), "")
    }
}
