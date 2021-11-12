package com.example.englishlearningapp.mvvm.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.englishlearningapp.R
import com.example.englishlearningapp.mvvm.views.admin.AdminLoginActivity
import com.example.englishlearningapp.mvvm.views.student.StudentLoginActivity

class ChooseRoleActivity: AppCompatActivity() {
    lateinit var student: AppCompatButton
    lateinit var admin: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_role)

        student = findViewById(R.id.choose_student)
        admin = findViewById(R.id.choose_admin)

        student.setOnClickListener {
            startActivity(Intent(this, StudentLoginActivity::class.java))
        }
        admin.setOnClickListener {
            startActivity(Intent(this, AdminLoginActivity::class.java))
        }
    }
}