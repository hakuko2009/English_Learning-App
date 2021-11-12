package com.example.englishlearningapp.mvvm.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.englishlearningapp.`interface`.LoginResultCallBack

class StudentLoginViewModelFactory(private val callBack: LoginResultCallBack): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StudentLoginViewModel(callBack) as T
    }
}