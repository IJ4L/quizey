package com.example.quizey

import RecyclerViewAdapter
import RetrofitClient
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizey.mata_pelajaran.RecyclerDataMapel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.await

class HomeActivity : AppCompatActivity(), RecyclerViewAdapter.OnItemClickListener {
    private var recyclerView: RecyclerView? = null
    private var recyclerDataArrayList = ArrayList<RecyclerDataMapel>()
    private lateinit var apiService: ApiService
    private lateinit var adapter: RecyclerViewAdapter
    private lateinit var username : TextView

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val email = intent.getStringExtra("EMAIL_KEY")
        val name = intent.getStringExtra("NAME_KEY")

        recyclerView = findViewById(R.id.idCourseRV)
        adapter = RecyclerViewAdapter(recyclerDataArrayList, this, this)

        apiService = RetrofitClient.retrofit.create(ApiService::class.java)

        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = GridLayoutManager(this, 2)

        username = findViewById(R.id.username)
        username.text = name

        lifecycleScope.launch(Dispatchers.Main) {
            try {
                val response = apiService.getCoursesData("IPS", email!!).await()

                if (response.message == "ok") {
                    val data = response.data

                    recyclerDataArrayList.clear()
                    recyclerDataArrayList.addAll(data.map { course ->
                        RecyclerDataMapel(course.course_name, course.url_cover, course.course_id, email)
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

    override fun onItemClick(position: Int) {
        
    }
}

