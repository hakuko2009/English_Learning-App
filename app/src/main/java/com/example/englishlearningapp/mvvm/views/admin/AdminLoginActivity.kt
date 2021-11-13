package com.example.englishlearningapp.mvvm.views.admin

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.englishlearningapp.R
import com.example.englishlearningapp.`interface`.LoginResultCallBack
import com.example.englishlearningapp.databinding.ActivityAdminLoginBinding
import com.example.englishlearningapp.mvvm.viewmodels.AdminLoginViewModel
import com.example.englishlearningapp.mvvm.viewmodels.AdminLoginViewModelFactory

class AdminLoginActivity: AppCompatActivity(), LoginResultCallBack {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)
        var binding : ActivityAdminLoginBinding = DataBindingUtil
            .setContentView(this, R.layout.activity_admin_login)
        binding.viewModel = ViewModelProvider(this,
            AdminLoginViewModelFactory(this)
        )[AdminLoginViewModel::class.java]
    }

    override fun onSuccess(token: String) {
        Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, AdminMainActivity::class.java)

        val sharedF: SharedPreferences = getSharedPreferences("forAdmin", MODE_PRIVATE)
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