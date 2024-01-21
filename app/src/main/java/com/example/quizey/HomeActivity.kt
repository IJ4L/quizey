package com.example.quizey

import RetrofitClient
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizey.mata_pelajaran.RecyclerDataMapel
import com.example.quizey.mata_pelajaran.RecyclerViewAdapter
import com.example.quizey.model.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var recyclerDataArrayList: ArrayList<RecyclerDataMapel>? = null
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        recyclerView = findViewById(R.id.idCourseRV)
        recyclerDataArrayList = ArrayList()

        apiService = RetrofitClient.retrofit.create(ApiService::class.java)

        apiService.getCoursesData("IPS", "alitopan@widyaedu.com").enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    if (data != null) {
                        val recyclerDataList = data.map { course ->
                            RecyclerDataMapel(course.course_name, course.course_id.toInt())
                        }
                        recyclerDataArrayList?.addAll(recyclerDataList)

                        // Display data in RecyclerView
                        setupRecyclerView()
                    }
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                // Handle API call failure
            }
        })
    }

    private fun setupRecyclerView() {
        val adapter = RecyclerViewAdapter(recyclerDataArrayList ?: ArrayList(), this)
        val layoutManager = GridLayoutManager(this, 2)

        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = adapter
    }
}

private fun <T> Call<T>.enqueue(apiResponseCallback: Callback<ApiResponse>) {
    TODO("Not yet implemented")
}
