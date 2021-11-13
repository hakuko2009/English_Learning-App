package com.example.englishlearningapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.englishlearningapp.`interface`.OnClickListener
import com.example.englishlearningapp.data.models.Teacher
import com.example.englishlearningapp.databinding.TeacherCardViewBinding

class TeacherAdapter(var teacherList: List<Teacher>): RecyclerView.Adapter<TeacherAdapter.MyViewHolder>() {
    private lateinit var onClickListener: OnClickListener

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = TeacherCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return teacherList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.setOnClickListener{
            onClickListener.onClick(holder.itemView, teacherList[position].username)
        }
        holder.bind(teacherList[position])
    }

    class MyViewHolder(private val binding: TeacherCardViewBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(teacher: Teacher){
            binding.gvUsername.text = "Tài khoản: " + teacher.username
            binding.gvName.text = "Họ và tên: " + teacher.name
            binding.gvIdNumb.text = "CMND/CCCD: " + teacher.idNumb
            binding.gvTel.text = "Tel: " + teacher.email
            binding.gvEmail.text = "Số bài học: " + teacher.email
        }
    }
}