package com.example.englishlearningapp.mvvm.views.student.main

import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.englishlearningapp.R
import com.example.englishlearningapp.`interface`.OnClickListener
import com.example.englishlearningapp.adapter.LessonAdapter
import com.example.englishlearningapp.data.models.Lesson
import com.example.englishlearningapp.mvvm.viewmodels.AllLessonViewModel
import com.example.englishlearningapp.mvvm.views.ChooseRoleActivity


class AllLessonsFragment constructor(var token: String) : Fragment() {
    private var lessonList: List<Lesson> = ArrayList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LessonAdapter
    private lateinit var logoutButton: Button
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var viewModel: AllLessonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(AllLessonViewModel::class.java)
        return inflater.inflate(R.layout.fragment_student_all_lessons, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.student_lessons_rv)
        logoutButton = view.findViewById(R.id.student_lessons_logout_btn)
        swipeRefreshLayout = view.findViewById(R.id.student_lessons_swipe_layout)

        logoutButton.setOnClickListener{
            startActivity(Intent(context, ChooseRoleActivity::class.java))
        }
        setAdapter()

    }
    private fun setAdapter(){
        viewModel.getAllLessonList(token)
        viewModel.liveLessonList.observe(viewLifecycleOwner, {
            lessonList = viewModel.liveLessonList.value!! as ArrayList
            if(lessonList.isNotEmpty()){
                swipeRefreshLayout.visibility = View.VISIBLE
                adapter = LessonAdapter(lessonList)
                recyclerView.layoutManager = GridLayoutManager(context, 1)
                recyclerView.adapter = adapter
                recyclerView.setHasFixedSize(true)

                adapter.setOnClickListener(object: OnClickListener {
                    override fun onClick(itemView: View, lessonId: String) {
                        val intent = Intent(context, LessonDetailActivity::class.java)
                        intent.putExtra("lessonId", lessonId)
                        startActivity(intent)
                    }
                })
            }else{
                swipeRefreshLayout.visibility = View.GONE
            }
        })
    }

}