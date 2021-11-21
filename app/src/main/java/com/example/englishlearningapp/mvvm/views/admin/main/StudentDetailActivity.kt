package com.example.englishlearningapp.mvvm.views.admin.main

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
import com.example.englishlearningapp.databinding.ActivityAdminStudentDetailBinding
import com.example.englishlearningapp.mvvm.viewmodels.ManageStudentsViewModel
import java.text.SimpleDateFormat

class StudentDetailActivity: AppCompatActivity(){
    lateinit var viewModel: ManageStudentsViewModel
    lateinit var binding: ActivityAdminStudentDetailBinding

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
        setContentView(R.layout.activity_admin_student_detail)

        val hvUsername = intent.getStringExtra("hv_username")!!
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Student's Information"
        viewModel = ViewModelProviders.of(this).get(ManageStudentsViewModel::class.java)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_admin_student_detail)
        binding.viewModel = ViewModelProvider(this)[ManageStudentsViewModel::class.java]

        binding.adminDetailStudentSwipeLayout.setOnRefreshListener {
            Handler().postDelayed({
                binding.adminDetailStudentSwipeLayout.isRefreshing = false }, 1000)
            setUI(hvUsername)
        }
        binding.adminDetailStudentBackBtn.setOnClickListener {
            this.onBackPressed()
        }
        setUI(hvUsername)
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun setUI(hv_username: String){
        viewModel.getStudentDetail(hv_username)
        viewModel.liveStudent.observe(this, {
            if(viewModel.liveStudent.value != null){
                val student = viewModel.liveStudent.value!!
                Log.d("student birthday", student.dayOfBirth!!)

                binding.adminDetailStudentUsername.text = "Account: $hv_username"
                binding.adminDetailStudentName.text = student.name
                val birthDay = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(student.dayOfBirth!!)
                binding.adminDetailStudentBirthday.text = SimpleDateFormat("dd-MM-yyyy").format(birthDay!!)
                binding.adminDetailStudentGender.text = when(student.gender){
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
                binding.adminDetailStudentEmail.text = student.email
                binding.adminDetailStudentTel.text = student.tel
                binding.adminDetailStudentAddress.text = student.address
                val registerDay = SimpleDateFormat("yyyy-dd-MM'T'HH:mm:ss.SSS'Z'").parse(student.dayOfReg!!)
                if(student.learntLessons != null){
                    binding.adminDetailStudentNumberofLesson.text = student.learntLessons.toString()
                }
                binding.adminDetailStudentDayofRegister.text = SimpleDateFormat("dd-MM-yyyy").format(registerDay!!)
                viewModel.liveStudent.removeObservers(this)
            }
        })
    }
}