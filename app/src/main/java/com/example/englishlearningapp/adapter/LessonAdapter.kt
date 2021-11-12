package com.example.englishlearningapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.englishlearningapp.R
import com.example.englishlearningapp.`interface`.OnClickListener
import com.example.englishlearningapp.data.models.Lesson
import com.example.englishlearningapp.databinding.LessonCardViewBinding

class LessonAdapter(var lessonList: List<Lesson>): RecyclerView.Adapter<LessonAdapter.MyViewHolder>() {

    private lateinit var onClickListener: OnClickListener
    lateinit var binding: LessonCardViewBinding

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.lesson_card_view, parent, false)
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                                        R.layout.lesson_card_view, parent, false)

        return MyViewHolder(onClickListener, v)
    }

    override fun getItemCount(): Int {
        return lessonList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        binding.lesson = lessonList[position]

        holder.itemView.setOnClickListener{
            onClickListener.onClick(holder.itemView, lessonList[position].lessonId)
        }
    }

    class MyViewHolder(onClickListener: OnClickListener, itemView: View): RecyclerView.ViewHolder(itemView)
}