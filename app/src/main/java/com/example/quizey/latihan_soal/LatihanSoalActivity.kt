package com.example.quizey.latihan_soal

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizey.R
import com.example.quizey.api_service.ApiService
import com.example.quizey.api_service.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.await

class LatihanSoalActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var recyclerDataArrayList = ArrayList<RecyclerLatihanSoal>()
    private lateinit var apiService: ApiService
    private lateinit var adapter: RecyclerViewAdapterSoal

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latihan_soal)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        toolbar.navigationIcon?.setTint(resources.getColor(android.R.color.white, theme))
        toolbar.title = ""

        val email = intent.getStringExtra("EMAIL_KEY")
        val mapel_id = intent.getStringExtra("MAPEL_ID")

        recyclerView = findViewById(R.id.latsol)
        adapter = RecyclerViewAdapterSoal(recyclerDataArrayList, this, this)

        apiService = RetrofitClient.retrofit.create(ApiService::class.java)

        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = GridLayoutManager(this, 2)

        lifecycleScope.launch(Dispatchers.Main) {
            try {
                val response = apiService.getExerciseData(mapel_id!!, email!!).await()

                if (response.message == "ok") {
                    val data = response.data

                    recyclerDataArrayList.clear()
                    recyclerDataArrayList.addAll(data.map { course ->
                        RecyclerLatihanSoal(course.exercise_title, course.icon, course.exercise_id, course.jumlah_soal, course.jumlah_done, email)
                    })

                    for (item in recyclerDataArrayList) {
                        Log.i("HomeActivity", "Course Name: ${item.title}, Course ID: ${item.imgid}")
                    }

                    adapter.notifyDataSetChanged()
                } else {

                    Log.e("HomeActivity", "API Error: ${response.status}")
                }
            } catch (e: Exception) {
                Log.e("HomeActivity", "Exception: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}