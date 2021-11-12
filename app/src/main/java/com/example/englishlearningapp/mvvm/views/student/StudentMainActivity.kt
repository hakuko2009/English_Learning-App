package com.example.englishlearningapp.mvvm.views.student

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.englishlearningapp.R
import com.example.englishlearningapp.mvvm.views.student.main.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class StudentMainActivity: AppCompatActivity() {
    lateinit var lessonFragment: AllLessonsFragment
    lateinit var accountFragment: AccountFragment

    private val fm: FragmentManager = supportFragmentManager
    private lateinit var currentFm: Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_main)

        val intent = intent
        val token = intent.getStringExtra("token").toString()

        val menu: BottomNavigationView = findViewById(R.id.bottom_navigation)
        menu.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        lessonFragment = AllLessonsFragment(token)
        accountFragment = AccountFragment(token)

        currentFm = lessonFragment

        fm.beginTransaction().add(R.id.student_main_frame_layout, accountFragment, "account").commit()
        fm.beginTransaction().add(R.id.student_main_frame_layout, lessonFragment, "lessons").commit()
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.student_lessons -> {
                fm.beginTransaction().hide(currentFm).show(lessonFragment).commit()
                currentFm = lessonFragment
                return@OnNavigationItemSelectedListener true
            }
            R.id.student_account -> {
                fm.beginTransaction().hide(currentFm).show(accountFragment).commit()
                currentFm = accountFragment
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}