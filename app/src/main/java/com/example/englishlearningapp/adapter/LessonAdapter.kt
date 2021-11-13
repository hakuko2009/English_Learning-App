package com.example.englishlearningapp.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.englishlearningapp.`interface`.OnClickListener
import com.example.englishlearningapp.data.models.Lesson
import com.example.englishlearningapp.databinding.LessonCardViewBinding

class LessonAdapter(var lessonList: List<Lesson>): RecyclerView.Adapter<LessonAdapter.MyViewHolder>() {

    private lateinit var onClickListener: OnClickListener
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = LessonCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return lessonList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.setOnClickListener{
            onClickListener.onClick(holder.itemView, lessonList[position].lessonId)
        }
        holder.bind(lessonList[position])
    }

    class MyViewHolder(private val binding: LessonCardViewBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(lesson: Lesson){
            binding.lessonName.text = lesson.lessonName
            binding.lessonContent.text = "Nội dung: " + lesson.content
            binding.lessonScore.text = "Đánh giá: " + lesson.score.toString()

            if(!binding.lessonName.text.isNullOrEmpty()){
                Log.d("Log not null", binding.lessonName.text.toString())
            }
        }
    }
}
