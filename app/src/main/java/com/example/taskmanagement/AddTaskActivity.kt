package com.example.taskmanagement

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.taskmanagement.databinding.ActivityAddTaskBinding
import com.example.taskmanagement.model.DataUtil
import com.example.taskmanagement.model.TaskModel
import com.example.taskmanagement.viewModel.TaskViewModel
import com.example.taskmanagement.R
import java.util.Calendar


class AddTaskActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var model: TaskModel
    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var taskDatabase: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskDatabase = ViewModelProvider(this)[TaskViewModel::class.java]
        initView()
        observeEvents()
    }


    private fun initView() {


        binding.btnBack.setOnClickListener(this)
        binding.tvDueDate.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)

    }

    private fun observeEvents() {
        taskDatabase.allTaskModel.observe(this) { list ->
            list?.let {
                // updates the list.
                // categoryAdapter.updateList(it)
            }
        }
    }


    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnBack -> {
                finish()
            }

            R.id.tvDueDate -> {
                showDatePickerDialog()
            }

            R.id.btnSubmit -> {
                if (isValidate())
                    saveData()
            }
        }
    }


    private fun isValidate(): Boolean {
        var isError = true
        when {
            DataUtil.isEmptyText(binding.edtProjectName) -> {
                isError = false
                binding.edtProjectName.requestFocus()
                Toast.makeText(this, "Please enter project name", Toast.LENGTH_SHORT).show()
            }

            DataUtil.isEmptyText(binding.edtProjectDesc) -> {
                isError = false
                binding.edtProjectDesc.requestFocus()
                Toast.makeText(this, "Please enter project description", Toast.LENGTH_SHORT).show()
            }

            DataUtil.isEmptyText(binding.tvDueDate) -> {
                isError = false
                binding.tvDueDate.requestFocus()
                Toast.makeText(this, "Please due date", Toast.LENGTH_SHORT).show()
            }

        }
        return isError
    }

    fun saveData() {

        model = TaskModel()
        model.projectName = DataUtil.getTextValue(binding.edtProjectName)
        model.projectDesc = DataUtil.getTextValue(binding.edtProjectDesc)
        model.date = DataUtil.getTextValue(binding.tvDueDate)

        taskDatabase.insertContacts(model)

        finish()

        Toast.makeText(this, "Task add successfully", Toast.LENGTH_SHORT).show()
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val monthsArray = arrayOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")

        val datePickerDialog = DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                val selectedDate = "$dayOfMonth ${monthsArray[month]}, $year"
                binding.tvDueDate.text = selectedDate
            }
        }, year, month, day)

        datePickerDialog.show()
    }
}