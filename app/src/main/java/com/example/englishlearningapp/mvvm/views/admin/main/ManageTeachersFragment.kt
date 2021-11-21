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
import com.example.englishlearningapp.adapter.TeacherAdapter
import com.example.englishlearningapp.data.models.Teacher
import com.example.englishlearningapp.mvvm.viewmodels.ManageTeachersViewModel

class ManageTeachersFragment constructor(var token: String): Fragment() {
    private var teacherList: List<Teacher> = ArrayList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TeacherAdapter
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var viewModel: ManageTeachersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onResume() {
        super.onResume()
        setAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(ManageTeachersViewModel::class.java)
        return inflater.inflate(R.layout.fragment_admin_manage_teachers, container, false);
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.admin_manage_teachers_rv)
        swipeRefreshLayout = view.findViewById(R.id.admin_manage_teachers_swipe_layout)

        swipeRefreshLayout.setOnRefreshListener {
            Handler().postDelayed({ swipeRefreshLayout.isRefreshing = false }, 1000)
            setAdapter()
        }
    }

    private fun setAdapter(){
        Log.d("Log set teacher adapter", "teacher list")
        viewModel.getTeacherList(token)
        viewModel.liveTeacherList.observe(viewLifecycleOwner, {
            teacherList = viewModel.liveTeacherList.value!! as ArrayList
            if(teacherList.isNotEmpty()){
                Log.d("Log list not empty: ", teacherList.size.toString())
                swipeRefreshLayout.visibility = View.VISIBLE
                adapter = TeacherAdapter(teacherList)
                recyclerView.layoutManager = LinearLayoutManager(this.context)
                recyclerView.adapter = adapter
                recyclerView.setHasFixedSize(true)
                adapter.notifyDataSetChanged()

                adapter.setOnClickListener(object: OnClickListener {
                    override fun onClick(itemView: View, username: String) {
                        val intent = Intent(context, TeacherDetailActivity::class.java)
                        intent.putExtra("gv_username", username)
                        // activity!!.finishAffinity()
                        startActivity(intent)
                    }
                })
            }else{
                swipeRefreshLayout.visibility = View.GONE
            }
        })
    }
}