package com.example.quizey

import RetrofitClient
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizey.mata_pelajaran.RecyclerDataMapel
import com.example.quizey.mata_pelajaran.RecyclerViewAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.await

class HomeActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var recyclerDataArrayList = ArrayList<RecyclerDataMapel>()
    private lateinit var apiService: ApiService
    private lateinit var adapter: RecyclerViewAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        recyclerView = findViewById(R.id.idCourseRV)
        adapter = RecyclerViewAdapter(recyclerDataArrayList, this)

        apiService = RetrofitClient.retrofit.create(ApiService::class.java)

        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = GridLayoutManager(this, 2)

        lifecycleScope.launch(Dispatchers.Main) {
            try {
                val response = apiService.getCoursesData("IPS", "alitopan@widyaedu.com").await()

                if (response.message == "ok") {
                    val data = response.data

                    recyclerDataArrayList.clear()
                    recyclerDataArrayList.addAll(data.map { course ->
                        RecyclerDataMapel(course.course_name, course.url_cover)
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
}
