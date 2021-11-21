package com.example.englishlearningapp.mvvm.views.admin.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.englishlearningapp.R
import com.example.englishlearningapp.`interface`.OnClickListener
import com.example.englishlearningapp.adapter.LessonAdapter
import com.example.englishlearningapp.data.models.Lesson
import com.example.englishlearningapp.mvvm.viewmodels.ManageLessonsViewModel

class ManageLessonsFragment constructor(var token: String): Fragment()  {
    private var lessonList: List<Lesson> = ArrayList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LessonAdapter
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var viewModel: ManageLessonsViewModel

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
        viewModel = ViewModelProviders.of(this).get(ManageLessonsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_admin_manage_lessons, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.admin_manage_lessons_rv)
        swipeRefreshLayout = view.findViewById(R.id.admin_lessons_swipe_layout)

        swipeRefreshLayout.setOnRefreshListener {
            Handler().postDelayed({ swipeRefreshLayout.isRefreshing = false }, 1000)
            setAdapter()
        }
        setAdapter()
    }

    private fun setAdapter(){
        viewModel.getLessonList(token)
        viewModel.liveLessonList.observe(viewLifecycleOwner, {
            lessonList = viewModel.liveLessonList.value!! as ArrayList
            if(lessonList.isNotEmpty()){
                Log.d("Log list not empty: ", lessonList.size.toString())
                swipeRefreshLayout.visibility = View.VISIBLE
                adapter = LessonAdapter(lessonList)
                recyclerView.layoutManager = LinearLayoutManager(this.context)
                recyclerView.adapter = adapter
                recyclerView.setHasFixedSize(true)
                adapter.notifyDataSetChanged()

                adapter.setOnClickListener(object: OnClickListener {
                    override fun onClick(itemView: View, lessonId: String) {
                        // detail cua student khac detail cua admin
                        val intent = Intent(context, AdminLessonDetailActivity::class.java)
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