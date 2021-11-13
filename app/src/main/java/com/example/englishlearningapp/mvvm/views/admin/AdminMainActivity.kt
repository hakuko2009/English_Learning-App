package com.example.englishlearningapp.mvvm.views.admin

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.englishlearningapp.R
import com.example.englishlearningapp.mvvm.views.ChooseRoleActivity
import com.example.englishlearningapp.mvvm.views.admin.main.AdminAccountActivity
import com.example.englishlearningapp.mvvm.views.admin.main.ManageStudentsFragment
import com.example.englishlearningapp.mvvm.views.admin.main.ManageTeachersFragment
import com.example.englishlearningapp.mvvm.views.student.StudentLoginActivity
import com.example.englishlearningapp.mvvm.views.student.main.AccountFragment
import com.example.englishlearningapp.mvvm.views.student.main.AllLessonsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class AdminMainActivity: AppCompatActivity() {
    lateinit var lessonFragment: AllLessonsFragment
    lateinit var studentFragment: ManageStudentsFragment
    lateinit var teacherFragment: ManageTeachersFragment
    private val fm: FragmentManager = supportFragmentManager
    private lateinit var currentFm: Fragment

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.admin_toolbar, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.admin_toolbar_account ->{
                startActivity(Intent(this, AdminAccountActivity::class.java))
            }
            R.id.admin_toolbar_logout -> {
                startActivity(Intent(this, ChooseRoleActivity::class.java))
            }
            R.id.admin_toolbar_seting -> {

            }

        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)

        val intent = intent
        val token = intent.getStringExtra("token").toString()

        val menu: BottomNavigationView = findViewById(R.id.admin_bottom_navigation)
        menu.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        lessonFragment = AllLessonsFragment(token)
        studentFragment = ManageStudentsFragment()
        teacherFragment = ManageTeachersFragment()

        currentFm = lessonFragment

        fm.beginTransaction().add(R.id.admin_main_frame_layout, lessonFragment, "lessons").commit()
        fm.beginTransaction().add(R.id.admin_main_frame_layout, studentFragment, "student").commit()
        fm.beginTransaction().add(R.id.admin_main_frame_layout, teacherFragment, "teacher").commit()
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.admin_manage_lessons -> {
                fm.beginTransaction().hide(currentFm).show(lessonFragment).commit()
                currentFm = lessonFragment
                return@OnNavigationItemSelectedListener true
            }
            R.id.admin_manage_students -> {
                fm.beginTransaction().hide(currentFm).show(studentFragment).commit()
                currentFm = studentFragment
                return@OnNavigationItemSelectedListener true
            }
            R.id.admin_manage_teachers -> {
                fm.beginTransaction().hide(currentFm).show(teacherFragment).commit()
                currentFm = teacherFragment
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

}