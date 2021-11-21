package com.example.englishlearningapp.mvvm.views.admin.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.englishlearningapp.R
import com.example.englishlearningapp.databinding.ActivityAdminLessonDetailBinding
import com.example.englishlearningapp.mvvm.viewmodels.ManageLessonsViewModel
import com.example.englishlearningapp.mvvm.views.ChooseRoleActivity
import com.example.englishlearningapp.mvvm.views.admin.AdminAccountActivity
import java.text.SimpleDateFormat

class AdminLessonDetailActivity: AppCompatActivity() {
    lateinit var viewModel: ManageLessonsViewModel
    lateinit var binding: ActivityAdminLessonDetailBinding

    override fun onBackPressed() {
        super.onBackPressed()
        supportFragmentManager.popBackStackImmediate()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->{
                supportFragmentManager.popBackStackImmediate()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_lesson_detail)

        val lessonId = intent.getStringExtra("lessonId").toString()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Update Lesson's Detail"
        viewModel = ViewModelProviders.of(this).get(ManageLessonsViewModel::class.java)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_admin_lesson_detail)
        binding.viewModel = ViewModelProvider(this)[ManageLessonsViewModel::class.java]

        binding.adminLessonDetailSwipeLayout.setOnRefreshListener {
            Handler().postDelayed({ binding.adminLessonDetailSwipeLayout.isRefreshing = false }, 1000)
            setUI(lessonId)
        }
        binding.adminDetailLessonBackBtn.setOnClickListener {
            this.onBackPressed()
        }
        setUI(lessonId)
    }

    @SuppressLint("SimpleDateFormat")
    private fun setUI(lessonId: String) {
        viewModel.getLessonDetail(lessonId)
        viewModel.liveLesson.observe(this, {
            if(viewModel.liveLesson.value != null){
                val lesson = viewModel.liveLesson.value!!

                binding.adminDetailLesssonID.text = lesson.lessonId
                binding.adminDetailLessonName.setText(lesson.lessonName)

                viewModel.getCategoryName(lesson.categoryId)
                viewModel.liveCategory.observe(this, {
                    if(viewModel.liveCategory.value != null){
                        val category = viewModel.liveCategory.value!!
                        binding.adminDetailLessonCategoryName.text =  category.categoryName
                        viewModel.liveCategory.removeObservers(this)
                    }
                })

                viewModel.getTeacherName(lesson.teacherUsername)
                viewModel.liveTeacher.observe(this, {
                    if(viewModel.liveTeacher.value != null){
                        val teacher = viewModel.liveTeacher.value!!
                        binding.adminDetailLesssonTeacherName.text =  teacher.name
                        viewModel.liveTeacher.removeObservers(this)
                    }
                })

                binding.adminDetailLesssonContent.setText(lesson.content)
                val gotDay = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(lesson.createdDay)
                binding.adminDetailLessonDay.text = SimpleDateFormat("yyyy-MM-dd").format(gotDay!!)

                binding.adminDetailLessonScore.text = lesson.scoreToString()

                binding.adminDetailLessonEditBtn.setOnClickListener {
                    binding.adminDetailLessonName.isEnabled = true
                    // binding.adminDetailLessonName.isFocusable = true
                    binding.adminDetailLesssonContent.isEnabled = true
                    binding.adminDetailLessonCompleteBtn.visibility = View.VISIBLE
                    binding.adminDetailLessonEditBtn.visibility = View.GONE
                }

                binding.adminDetailLessonCompleteBtn.setOnClickListener {
                    binding.adminDetailLessonName.isEnabled = false
                    binding.adminDetailLesssonContent.isEnabled = false

                    lesson.createdDay = binding.adminDetailLessonDay.text.toString()

                    viewModel.onCompleteClicked(lesson)
                    viewModel.liveUpdateResult.observe(this, {
                        if(!viewModel.liveUpdateResult.value.isNullOrEmpty()){
                            val mess = viewModel.liveUpdateResult.value!!
                            Toast.makeText(this, mess, Toast.LENGTH_SHORT).show()
                        }
                    })

                    binding.adminDetailLessonCompleteBtn.visibility = View.GONE
                    binding.adminDetailLessonEditBtn.visibility = View.VISIBLE
                }
                viewModel.liveLesson.removeObservers(this)
            }
        })
    }
}