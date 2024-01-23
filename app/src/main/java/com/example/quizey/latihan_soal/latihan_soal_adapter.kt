package com.example.quizey.latihan_soal

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizey.R
import com.example.quizey.kerjakan_soal.WorkExerciseActivity
import com.squareup.picasso.Picasso

class RecyclerViewAdapterSoal(
    private val courseDataArrayList: ArrayList<RecyclerLatihanSoal>,
    private val mcontext: Context,
    private val onItemClickListener: LatihanSoalActivity
) :
    RecyclerView.Adapter<RecyclerViewAdapterSoal.RecyclerViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.card_latihan_soal, parent, false)
        return RecyclerViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val recyclerData = courseDataArrayList[position]

        holder.courseTV.text = recyclerData.title
        Picasso.get().load(recyclerData.imgid).into(holder.courseIV)
        holder.jumlahPaket.text = "${recyclerData.jmlDone} / ${recyclerData.jmlSoal} soal latihan"

        holder.itemView.setOnClickListener {
            val intent = Intent(mcontext, WorkExerciseActivity::class.java)
            intent.putExtra("EXERCISE_ID", recyclerData.id)
            intent.putExtra("EMAIL_KEY", recyclerData.email)
            mcontext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return courseDataArrayList.size
    }

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseTV: TextView = itemView.findViewById<TextView>(R.id.idTVCourse)
        val courseIV: ImageView = itemView.findViewById<ImageView>(R.id.idIVcourseIV)
        val jumlahPaket: TextView = itemView.findViewById<TextView>(R.id.subtitle)
    }
}
