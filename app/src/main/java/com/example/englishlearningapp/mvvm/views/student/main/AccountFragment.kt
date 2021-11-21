package com.example.englishlearningapp.mvvm.views.student.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.englishlearningapp.R
import com.example.englishlearningapp.data.models.Student
import com.example.englishlearningapp.databinding.ActivityAdminLessonDetailBinding
import com.example.englishlearningapp.databinding.FragmentStudentAccountBinding
import com.example.englishlearningapp.mvvm.models.UpdateStudentAccountModel
import com.example.englishlearningapp.mvvm.viewmodels.ManageLessonsViewModel
import com.example.englishlearningapp.mvvm.viewmodels.StudentAccountViewModel
import com.example.englishlearningapp.mvvm.views.student.StudentMainActivity
import java.text.SimpleDateFormat

class AccountFragment constructor(var token: String, var hv_username: String): Fragment() {
    lateinit var viewModel: StudentAccountViewModel
    lateinit var binding: FragmentStudentAccountBinding
    lateinit var adapter: ArrayAdapter<String>
    lateinit var genderList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        inflater.inflate(R.layout.fragment_student_account, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_student_account,
                                container, false)
        viewModel = ViewModelProviders.of(this).get(StudentAccountViewModel::class.java)

        binding.viewModel = ViewModelProvider(this)[StudentAccountViewModel::class.java]
        return binding.root
        //FragmentTest binding = DataBindingUtil.inflate(inflater, R.layout.fragment_test, container, false);
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.studentAccountUsername.text = "Account: $hv_username"
        setUI(hv_username)
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun setUI(hvUsername: String) {
        viewModel.getAccountInfo(hvUsername)
        viewModel.liveStudent.observe(viewLifecycleOwner, {
            val student = viewModel.liveStudent.value
            if(student != null){
                binding.studentAccountName.setText(student.name)
                val gotDay = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(student.dayOfBirth!!)
                binding.studentAccountBirthday.setText(SimpleDateFormat("dd/MM/yyyy").format(gotDay!!))

                genderList = ArrayList()
                genderList.addAll(resources.getStringArray(R.array.gender))
                setSpinnerAdapter(genderList, binding)
                //  val posCurrentCity = cityAdapter.getPosition(viewModel.cityName.value!!)
                //                                    citySpinner.setSelection(posCurrentCity, true)

                if(student.gender == null){
                    binding.studentAccountGenderSpinner.setSelection(2, true)
                }
                binding.studentAccountGenderSpinner.setSelection(student.gender!!, true)

                binding.studentAccountEmail.setText(student.email)
                binding.studentAccountTel.setText(student.tel)
                binding.studentAccountAddress.setText(student.address)

                binding.studentAccountEditBtn.setOnClickListener {
                    if(!binding.studentAccountName.isEnabled){
                        binding.studentAccountName.isEnabled = true
                        binding.studentAccountBirthday.isEnabled = true
                        binding.studentAccountEmail.isEnabled = true
                        binding.studentAccountTel.isEnabled = true
                        binding.studentAccountAddress.isEnabled = true
                        binding.studentAccountEditBtn.text = "Hoàn thành"
                        binding.studentAccountCancelBtn.visibility = View.VISIBLE
                        binding.studentAccountCancelBtn.setOnClickListener {
                            setUI(hv_username)
                            binding.studentAccountCancelBtn.visibility = View.GONE
                            binding.studentAccountName.isEnabled = false
                            binding.studentAccountBirthday.isEnabled = false
                            binding.studentAccountEmail.isEnabled = false
                            binding.studentAccountTel.isEnabled = false
                            binding.studentAccountAddress.isEnabled = false
                        }

                        binding.studentAccountGenderSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                val gender = adapter.getPosition(binding.studentAccountGenderSpinner.selectedItem.toString())
                                if(gender != 2){
                                    student.gender = gender
                                }
                            }
                            override fun onNothingSelected(parent: AdapterView<*>?) {
                            }
                        }
                    }else{
                        binding.studentAccountName.isEnabled = false
                        binding.studentAccountBirthday.isEnabled = false
                        binding.studentAccountEmail.isEnabled = false
                        binding.studentAccountTel.isEnabled = false
                        binding.studentAccountAddress.isEnabled = false

                        //   val gotDay = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(student.dayOfBirth!!)
                        //                binding.studentAccountBirthday.setText(SimpleDateFormat("dd-MM-yyyy").format(gotDay!!))

                        viewModel.updateAccountInfo(student.username, student.password, student.gender)
                        viewModel.liveUpdateResult.observe(viewLifecycleOwner, {
                            if(!viewModel.liveUpdateResult.value.isNullOrEmpty()){
                                val mess = viewModel.liveUpdateResult.value!!
                                Toast.makeText(requireContext(), mess, Toast.LENGTH_SHORT).show()
                            }
                        })
                        binding.studentAccountEditBtn.text = "Chỉnh sửa"
                        binding.studentAccountCancelBtn.visibility = View.GONE
                    }
                }
            }
        })
    }
    private fun setSpinnerAdapter(list: ArrayList<String>, binding: FragmentStudentAccountBinding){
        adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.studentAccountGenderSpinner.adapter = adapter

    }

}