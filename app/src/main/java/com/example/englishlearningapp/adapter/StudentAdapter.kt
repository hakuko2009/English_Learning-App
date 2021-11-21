package com.example.englishlearningapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.englishlearningapp.`interface`.OnClickListener
import com.example.englishlearningapp.data.models.Student
import com.example.englishlearningapp.databinding.StudentCardViewBinding
import java.text.SimpleDateFormat

class StudentAdapter(private var studentList: List<Student>): RecyclerView.Adapter<StudentAdapter.MyViewHolder>() {
    private lateinit var onClickListener: OnClickListener
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = StudentCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.setOnClickListener{
            onClickListener.onClick(holder.itemView, studentList[position].username)
        }
        holder.bind(studentList[position])
    }

    class MyViewHolder(private val binding: StudentCardViewBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(student: Student){
            binding.hvUsername.text = "Account: " + student.username
            binding.hvName.text = "Họ tên: " + student.name
            binding.hvEmail.text = "Email: " + student.email
            val gotDay = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(student.dayOfReg!!)
            binding.hvRegisterDay.text = "Ngày đăng ký: " + SimpleDateFormat("dd-MM-yyyy").format(gotDay!!)
            // 2001-12-11T17:00:00.000Z
        }
    }
}