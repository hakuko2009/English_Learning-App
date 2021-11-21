package com.example.englishlearningapp.mvvm.views.admin

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.englishlearningapp.R
import com.example.englishlearningapp.databinding.ActivityAdminAccountBinding
import com.example.englishlearningapp.databinding.FragmentStudentAccountBinding
import com.example.englishlearningapp.mvvm.viewmodels.AdminAccountViewModel
import com.example.englishlearningapp.mvvm.viewmodels.StudentAccountViewModel

class AdminAccountActivity: AppCompatActivity() {
    lateinit var viewModel: AdminAccountViewModel
    lateinit var binding: ActivityAdminAccountBinding
    lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_account)

        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.title = "Administrator Account"
        username = intent.getStringExtra("admin_username")!!
        viewModel =  ViewModelProviders.of(this).get(AdminAccountViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_admin_account)
        binding.viewModel = ViewModelProvider(this)[AdminAccountViewModel::class.java]

        binding.adminAccountUsername.text = username
        setUI(username)
    }

    @SuppressLint("SetTextI18n")
    private fun setUI(username: String) {
        viewModel.getAccountInfo(username)
        viewModel.liveAdmin.observe(this, {
            if(viewModel.liveAdmin.value != null){
                val info = viewModel.liveAdmin.value!!

                binding.adminAccountEmail.setText(info.email)
                binding.adminAccountPhone.setText(info.tel)

                binding.adminAccountEditBtn.setOnClickListener {
                    if(!binding.adminAccountEmail.isEnabled){
                        binding.adminAccountEmail.isEnabled = true
                        // binding.adminAccountEmail.isFocusable = true
                        binding.adminAccountPhone.isEnabled = true

                        binding.adminAccountEditBtn.text = "Hoàn thành"
                        binding.adminAccountCancelBtn.visibility = View.VISIBLE
                        binding.adminAccountCancelBtn.setOnClickListener {
                            setUI(username)
                            binding.adminAccountCancelBtn.visibility = View.GONE
                            binding.adminAccountEmail.isEnabled = false
                            binding.adminAccountPhone.isEnabled = false
                        }
                    }else{
                        binding.adminAccountEmail.isEnabled = false
                        binding.adminAccountPhone.isEnabled = false

                        viewModel.updateAccountInfo(username, info.password)
                        viewModel.liveUpdateResult.observe(this, {
                            if(!viewModel.liveUpdateResult.value.isNullOrEmpty()){
                                val mess = viewModel.liveUpdateResult.value!!
                                Toast.makeText(this, mess, Toast.LENGTH_SHORT).show()
                            }
                        })
                        binding.adminAccountCancelBtn.visibility = View.GONE
                        binding.adminAccountEditBtn.text = "Chỉnh sửa"
                    }
                }
            }
        })

    }
}