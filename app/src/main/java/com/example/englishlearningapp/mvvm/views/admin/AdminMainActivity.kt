package com.example.englishlearningapp.mvvm.views.admin

import android.app.ActionBar
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.englishlearningapp.R
import com.example.englishlearningapp.mvvm.views.ChooseRoleActivity
import com.example.englishlearningapp.mvvm.views.admin.main.ManageLessonsFragment
import com.example.englishlearningapp.mvvm.views.admin.main.ManageStudentsFragment
import com.example.englishlearningapp.mvvm.views.admin.main.ManageTeachersFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class AdminMainActivity: AppCompatActivity() {
    lateinit var lessonFragment: ManageLessonsFragment
    lateinit var studentFragment: ManageStudentsFragment
    lateinit var teacherFragment: ManageTeachersFragment
    private val fm: FragmentManager = supportFragmentManager
    private lateinit var currentFm: Fragment
    private lateinit var username: String

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.admin_toolbar, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.admin_toolbar_account ->{
                val sharedF: SharedPreferences = getSharedPreferences("forAdmin", MODE_PRIVATE)
                val username = sharedF.getString("admin_username", null)

                val intent = Intent(this, AdminAccountActivity::class.java)
                intent.putExtra("admin_username", username)
                startActivity(intent)
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
        val token = intent.getStringExtra("token").toString()
        username = intent.getStringExtra("admin_username").toString()

        val menu: BottomNavigationView = findViewById(R.id.admin_bottom_navigation)
        menu.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        supportActionBar!!.title = "Administrator Home"

        lessonFragment = ManageLessonsFragment(token)
        studentFragment = ManageStudentsFragment(token)
        teacherFragment = ManageTeachersFragment(token)

        currentFm = lessonFragment

        fm.beginTransaction().add(R.id.admin_main_frame_layout, lessonFragment, "lessons").commit()
        fm.beginTransaction().add(R.id.admin_main_frame_layout, studentFragment, "student")
                        .hide(studentFragment).commit()
        fm.beginTransaction().add(R.id.admin_main_frame_layout, teacherFragment, "teacher")
                        .hide(teacherFragment).commit()
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
                Log.d("admin manage teacher", "Log" )
                fm.beginTransaction().hide(currentFm).show(teacherFragment).commit()
                currentFm = teacherFragment
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

}