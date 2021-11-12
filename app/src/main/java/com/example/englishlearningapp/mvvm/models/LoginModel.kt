package com.example.englishlearningapp.mvvm.models

data class LoginModel(
    var username: String,
    var password: String) {

    fun validInfor(): Int {
        if(validateUsername() && password.length < 6){
            return 1
        }
        if(!validateUsername() && password.length >= 6){
            return 2
        }
        return if(!validateUsername() && password.length < 6){
            3
        } else{
            4
        }
    }

    private fun validateUsername(): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        if (username.matches(emailPattern.toRegex())) {
            return true
        }
        return false
    }

}