
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizey.HomeActivity
import com.example.quizey.LatihanSoalActivity
import com.example.quizey.R
import com.example.quizey.mata_pelajaran.RecyclerDataMapel
import com.squareup.picasso.Picasso

class RecyclerViewAdapter(
    private val courseDataArrayList: ArrayList<RecyclerDataMapel>,
    private val mcontext: Context,
    private val onItemClickListener: HomeActivity
) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.card_mata_pelajaran, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val recyclerData = courseDataArrayList[position]

        holder.courseTV.text = recyclerData.title
        Picasso.get().load(recyclerData.imgid).into(holder.courseIV)

        holder.itemView.setOnClickListener {
            val intent = Intent(mcontext, LatihanSoalActivity::class.java)
            intent.putExtra("MAPEL_ID", recyclerData.idMapel)
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
    }
}
