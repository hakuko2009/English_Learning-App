package com.example.englishlearningapp.mvvm.views.admin.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.englishlearningapp.R
import com.example.englishlearningapp.`interface`.OnClickListener
import com.example.englishlearningapp.adapter.LessonAdapter
import com.example.englishlearningapp.databinding.ActivityAdminTeacherDetailBinding
import com.example.englishlearningapp.mvvm.viewmodels.ManageStudentsViewModel
import com.example.englishlearningapp.mvvm.viewmodels.ManageTeachersViewModel
import java.text.SimpleDateFormat

class TeacherDetailActivity: AppCompatActivity() {
    lateinit var viewModel: ManageTeachersViewModel
    private lateinit var listAdapter: LessonAdapter
    lateinit var binding: ActivityAdminTeacherDetailBinding

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_teacher_detail)
        val gvUsername = intent.getStringExtra("gv_username")!!

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Teacher's Information"
        viewModel = ViewModelProviders.of(this).get(ManageTeachersViewModel::class.java)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_admin_teacher_detail)
        binding.viewModel = ViewModelProvider(this)[ManageTeachersViewModel::class.java]

        binding.adminDetailTeacherSwipeLayout.setOnRefreshListener {
            Handler().postDelayed({
                binding.adminDetailTeacherSwipeLayout.isRefreshing = false }, 1000)
            setUI(gvUsername)
        }
        binding.adminDetailTeacherBackBtn.setOnClickListener {
            this.onBackPressed()
        }
        setUI(gvUsername)
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun setUI(gvUsername: String) {
        viewModel.getTeacherDetail(gvUsername)
        viewModel.liveTeacher.observe(this, {
            if(viewModel.liveTeacher.value != null){
                val teacher = viewModel.liveTeacher.value!!
                Log.d("teacher birthday", teacher.dayOfBirth!!)

                binding.adminDetailTeacherUsername.text = "Account: $gvUsername"
                binding.adminDetailTeacherName.text = teacher.name
                binding.adminDetailTeacherIDNumber.text = "ID Number: ${teacher.idNumb}"
                val birthDay = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(teacher.dayOfBirth!!)
                binding.adminDetailTeacherBirthday.text = SimpleDateFormat("dd-MM-yyyy").format(birthDay!!)
                binding.adminDetailTeacherGender.text = when(teacher.gender){
                    0 -> {
                        "Nam"
                    }
                    1 -> {
                        "Nữ"
                    }
                    else -> {
                        ""
                    }
                }
                binding.adminDetailTeacherEmail.text = teacher.email
                binding.adminDetailTeacherTel.text = teacher.tel
                binding.adminDetailTeacherAddress.text = teacher.address
                binding.adminDetailTeacherDVCT.text = teacher.workingPlace

                setListAdapter(gvUsername, binding)

                viewModel.liveTeacher.removeObservers(this)
            }
        })
    }
    private fun setListAdapter(gvUsername: String, binding: ActivityAdminTeacherDetailBinding){
        viewModel.getAllLessonbyTeacher(gvUsername)
        viewModel.liveLessonListbyTeacher.observe(this, {
            val value = viewModel.liveLessonListbyTeacher.value as ArrayList
            if(!value.isNullOrEmpty()){
                val lessonList = viewModel.liveLessonListbyTeacher.value!!
                if(lessonList.isNotEmpty()){
                    Log.d("Log list gv lesson", lessonList.size.toString())
                    listAdapter = LessonAdapter(lessonList)

                    val rv = binding.adminManageTeacherLessonsRv
                    rv.layoutManager = LinearLayoutManager(this)
                    rv.adapter = listAdapter
                    rv.setHasFixedSize(true)
                    listAdapter.notifyDataSetChanged()

                    listAdapter.setOnClickListener(object: OnClickListener {
                        override fun onClick(itemView: View, lessonId: String) {
                            // detail cua student khac detail cua admin
                            val intent = Intent(baseContext, AdminLessonDetailActivity::class.java)
                            intent.putExtra("lessonId", lessonId)
                            startActivity(intent)
                        }
                    })
                }
            }
        })
    }
}