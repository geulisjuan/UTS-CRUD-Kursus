package andini.com.adapter

import andini.com.databinding.CourseRvItemBinding
import andini.com.model.CourseRVModal
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class CourseRVAdapter(
    private val courseRVModalArrayList: ArrayList<CourseRVModal?>?,
    private val context: Context,
    private val courseClickInterface: CourseClickInterface
) :
    RecyclerView.Adapter<CourseRVAdapter.ViewHolder>() {
    private var lastPos = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CourseRvItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val courseRVModal = courseRVModalArrayList?.get(position)
        if (courseRVModal != null) {
            holder.binding.namaKursus.text = courseRVModal.courseName
            holder.binding.biayaKursus.text = "RP. " + courseRVModal.coursePrice
            Picasso.get().load(courseRVModal.courseImg).into(holder.binding.gambarKursus)
        }
        setAnimation(holder.itemView, position)
        holder.binding.gambarKursus.setOnClickListener {
            courseClickInterface.onCourseClick(
                position
            )
        }
    }

    private fun setAnimation(itemView: View, position: Int) {
        if (position > lastPos) {
            val animation = AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_in)
            itemView.animation = animation
            lastPos = position
        }
    }

    override fun getItemCount(): Int {
        return courseRVModalArrayList!!.size
    }

    class ViewHolder(var binding: CourseRvItemBinding) : RecyclerView.ViewHolder(binding.root)

    interface CourseClickInterface {
        fun onCourseClick(position: Int)
    }
}