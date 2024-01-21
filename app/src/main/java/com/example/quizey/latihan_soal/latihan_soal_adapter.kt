package com.example.quizey.latihan_soal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizey.R
import com.squareup.picasso.Picasso

class RecyclerViewAdapterSoal(
    private val courseDataArrayList: ArrayList<RecyclerLatihanSoal>,
    private val mcontext: Context
) :
    RecyclerView.Adapter<RecyclerViewAdapterSoal.RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.card_mata_pelajaran, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val recyclerData = courseDataArrayList[position]

        holder.courseTV.text = recyclerData.title
        Picasso.get().load(recyclerData.imgid).into(holder.courseIV)
    }

    override fun getItemCount(): Int {
        return courseDataArrayList.size
    }

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseTV: TextView = itemView.findViewById<TextView>(R.id.idTVCourse)
        val courseIV: ImageView = itemView.findViewById<ImageView>(R.id.idIVcourseIV)
    }
}
