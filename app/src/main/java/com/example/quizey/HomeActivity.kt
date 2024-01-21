package com.example.quizey

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizey.mata_pelajaran.RecyclerDataMapel
import com.example.quizey.mata_pelajaran.RecyclerViewAdapter

class HomeActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var recyclerDataArrayList: ArrayList<RecyclerDataMapel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        recyclerView = findViewById(R.id.idCourseRV)

        recyclerDataArrayList = ArrayList()

        recyclerDataArrayList?.apply {
            add(RecyclerDataMapel("DSA", 1))
            add(RecyclerDataMapel("JAVA", 2))
            add(RecyclerDataMapel("C++", 3))
            add(RecyclerDataMapel("Python", 4))
            add(RecyclerDataMapel("Node Js", 5))
        }

        val adapter = RecyclerViewAdapter(recyclerDataArrayList ?: ArrayList(), this)

        val layoutManager = GridLayoutManager(this, 2)

        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = adapter
    }
}