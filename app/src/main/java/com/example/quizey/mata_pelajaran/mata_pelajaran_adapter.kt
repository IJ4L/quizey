package com.example.quizey.mata_pelajaran

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizey.R

class RecyclerViewAdapter(
    private val courseDataArrayList: ArrayList<RecyclerDataMapel>,
    private val mcontext: Context
) :
    RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.card_mata_pelajaran, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val recyclerData = courseDataArrayList[position]

        holder.courseTV.text = recyclerData.title
//        holder.courseIV.setImageResource(recyclerData.imgid)
    }

    override fun getItemCount(): Int {
        return courseDataArrayList.size
    }

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseTV: TextView = itemView.findViewById<TextView>(R.id.idTVCourse)
//        val courseIV: ImageView = itemView.findViewById<ImageView>(R.id.idIVcourseIV)
    }
}
