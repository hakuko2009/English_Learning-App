package com.example.englishlearningapp.mvvm.views.student.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.englishlearningapp.R
import com.example.englishlearningapp.databinding.ActivityStudentLessonDetailBinding
import com.example.englishlearningapp.mvvm.viewmodels.ManageLessonsViewModel
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

class StudentLessonDetailActivity: AppCompatActivity() {
    lateinit var viewModel: ManageLessonsViewModel
    lateinit var binding: ActivityStudentLessonDetailBinding

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->{
                supportFragmentManager.popBackStackImmediate()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onBackPressed() {
        super.onBackPressed()
        supportFragmentManager.popBackStackImmediate()
    }
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_lesson_detail)

        val lessonId = intent.getStringExtra("lessonId").toString()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Lesson's Detail"
        viewModel = ViewModelProviders.of(this).get(ManageLessonsViewModel::class.java)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_student_lesson_detail)
        binding.viewModel = ViewModelProvider(this)[ManageLessonsViewModel::class.java]

        binding.studentLessonDetailSwipeLayout.setOnRefreshListener {
            Handler().postDelayed({
                binding.studentLessonDetailSwipeLayout.isRefreshing = false }, 1000)
            setUI(lessonId)
        }

        binding.studentDetailLessonBackBtn.setOnClickListener {
            this.onBackPressed()
        }
        setUI(lessonId)
    }

    @SuppressLint("SimpleDateFormat")
    private fun setUI(lessonId: String){
        viewModel.getLessonDetail(lessonId)
        viewModel.liveLesson.observe(this, {
            if(viewModel.liveLesson.value != null){
                val lesson = viewModel.liveLesson.value!!
                Log.d("lesson time", lesson.createdDay)

                binding.studentDetailLesssnName.text = lesson.lessonName

                viewModel.getCategoryName(lesson.categoryId)
                viewModel.liveCategory.observe(this, {
                    if(viewModel.liveCategory.value != null){
                        val category = viewModel.liveCategory.value!!
                        binding.studentDetailLessonCategoryName.text =  category.categoryName
                        viewModel.liveCategory.removeObservers(this)
                    }
                })

                viewModel.getTeacherName(lesson.teacherUsername)
                viewModel.liveTeacher.observe(this, {
                    if(viewModel.liveTeacher.value != null){
                        val teacher = viewModel.liveTeacher.value!!
                        binding.studentDetailLesssonTeacherName.text =  teacher.name
                        viewModel.liveTeacher.removeObservers(this)
                    }
                })
                binding.studentDetailLesssonContent.text = lesson.content
                val gotDay = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(lesson.createdDay)
                binding.studentDetailLessonDay.text = SimpleDateFormat("dd-MM-yyyy").format(gotDay!!)

                binding.studentDetailLessonScore.text = lesson.scoreToString()
                viewModel.liveLesson.removeObservers(this)
            }
        })
    }
}