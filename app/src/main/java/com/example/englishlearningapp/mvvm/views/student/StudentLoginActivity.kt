package com.example.englishlearningapp.mvvm.views.student

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.englishlearningapp.R
import com.example.englishlearningapp.`interface`.LoginResultCallBack
import com.example.englishlearningapp.databinding.ActivityStudentLoginBinding
import com.example.englishlearningapp.mvvm.viewmodels.StudentLoginViewModel
import com.example.englishlearningapp.mvvm.viewmodels.StudentLoginViewModelFactory

class StudentLoginActivity: AppCompatActivity(), LoginResultCallBack {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_login)
        var binding : ActivityStudentLoginBinding = DataBindingUtil
                                        .setContentView(this,R.layout.activity_student_login)
        binding.viewModel = ViewModelProvider(this,
                    StudentLoginViewModelFactory(this))[StudentLoginViewModel::class.java]
    }

    override fun onSuccess(token: String) {
        Toast.makeText(this,"Đăng nhập thành công!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this,StudentMainActivity::class.java)

        val sharedF: SharedPreferences = getSharedPreferences("forStudent", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedF.edit()
        editor.putString("token",token)
        editor.apply()

        intent.putExtra("token",token)
        startActivity(intent)
    }

    override fun onError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}