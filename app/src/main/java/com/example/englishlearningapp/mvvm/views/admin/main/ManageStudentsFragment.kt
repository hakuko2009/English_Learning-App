package com.example.englishlearningapp.mvvm.views.admin.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
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
import com.example.englishlearningapp.adapter.StudentAdapter
import com.example.englishlearningapp.data.models.Student
import com.example.englishlearningapp.mvvm.viewmodels.ManageStudentsViewModel

class ManageStudentsFragment constructor(var token: String): Fragment() {
    private var studentList: List<Student> = ArrayList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentAdapter
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var viewModel: ManageStudentsViewModel

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
        viewModel = ViewModelProviders.of(this).get(ManageStudentsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_admin_manage_students, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.admin_manage_students_rv)
        swipeRefreshLayout = view.findViewById(R.id.admin_manage_students_swipe_layout)

        swipeRefreshLayout.setOnRefreshListener {
            Handler().postDelayed({ swipeRefreshLayout.isRefreshing = false }, 1000)
            setAdapter()
        }
        setAdapter()
    }
    private fun setAdapter(){
        viewModel.getStudentList(token)
        viewModel.liveStudentList.observe(viewLifecycleOwner, {
            studentList = viewModel.liveStudentList.value!! as ArrayList
            if(studentList.isNotEmpty()){
                // Log.d("Log list not empty: ", studentList.size.toString())
                swipeRefreshLayout.visibility = View.VISIBLE
                adapter = StudentAdapter(studentList)
                recyclerView.layoutManager = LinearLayoutManager(this.context)
                recyclerView.adapter = adapter
                recyclerView.setHasFixedSize(true)
                adapter.notifyDataSetChanged()

                adapter.setOnClickListener(object: OnClickListener {
                    override fun onClick(itemView: View, username: String) {
                        val intent = Intent(context, StudentDetailActivity::class.java)
                        intent.putExtra("hv_username", username)
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