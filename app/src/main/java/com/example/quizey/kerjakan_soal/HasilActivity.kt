package com.example.quizey.kerjakan_soal

import ExerciseModelDone
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.quizey.R
import com.example.quizey.api_service.ApiService
import com.example.quizey.api_service.RetrofitClient
import com.example.quizey.mata_pelajaran.HomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HasilActivity : AppCompatActivity() {
    private lateinit var jumlahScoreTextView: TextView
    private lateinit var buttonHome: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hasil)

        jumlahScoreTextView = findViewById(R.id.nilai)
        buttonHome = findViewById(R.id.home)

        val email = intent.getStringExtra("EMAIL_KEY").toString()
        val idExercise = intent.getStringExtra("EXERCISE_ID").toString()

        val exerciseService = RetrofitClient.retrofit.create(ApiService::class.java)
        val call = exerciseService.getValueTest(idExercise, email)

        call.enqueue(object : Callback<ExerciseModelDone> {
            override fun onResponse(call: Call<ExerciseModelDone>, response: Response<ExerciseModelDone>) {
                if (response.isSuccessful) {
                    val resultData = response.body()?.data
                    val score = resultData?.result?.jumlahBenar!! * 10
                    jumlahScoreTextView.text = "$score"
                }
            }

            override fun onFailure(call: Call<ExerciseModelDone>, t: Throwable) {
                // Handle kesalahan jaringan atau pemanggilan API
            }
        })

        buttonHome.setOnClickListener {
            val intent = Intent(this@HasilActivity, HomeActivity::class.java)
            intent.putExtra("EMAIL_KEY", email)
            startActivity(intent)
            finish()
        }
    }
}
