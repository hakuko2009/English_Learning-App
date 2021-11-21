package com.example.englishlearningapp.mvvm.views.admin

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.englishlearningapp.R
import com.example.englishlearningapp.`interface`.LoginResultCallBack
import com.example.englishlearningapp.databinding.ActivityAdminLoginBinding
import com.example.englishlearningapp.mvvm.viewmodels.AdminLoginViewModel
import com.example.englishlearningapp.mvvm.viewmodels.AdminLoginViewModelFactory
import com.example.englishlearningapp.mvvm.views.ChooseRoleActivity

class AdminLoginActivity: AppCompatActivity(), LoginResultCallBack {
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                 onBackPressed()
                 // startActivity(Intent(this, ChooseRoleActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val binding : ActivityAdminLoginBinding = DataBindingUtil
            .setContentView(this, R.layout.activity_admin_login)
        binding.viewModel = ViewModelProvider(this,
            AdminLoginViewModelFactory(this)
        )[AdminLoginViewModel::class.java]
    }

    override fun onSuccess(token: String, admin_username: String) {
        Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, AdminMainActivity::class.java)

        val sharedF: SharedPreferences = getSharedPreferences("forAdmin", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedF.edit()
        editor.putString("token", token)
        editor.putString("admin_username", admin_username)
        editor.apply()

        intent.putExtra("token", token)
        intent.putExtra("admin_username", admin_username)
        startActivity(intent)
    }

    override fun onError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}