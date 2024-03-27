package com.example.taskmanagement

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanagement.adapter.TaskListAdapter
import com.example.taskmanagement.databinding.ActivityTaskListBinding
import com.example.taskmanagement.model.TaskModel
import com.example.taskmanagement.viewModel.TaskViewModel
import com.example.taskmanagement.R


@SuppressLint("NotifyDataSetChanged")
class TaskListActivity : AppCompatActivity(), View.OnClickListener {

    private var lastPos: Int = -1
    lateinit var taskListAdapter: TaskListAdapter
    lateinit var viewModalContacts: TaskViewModel
    private lateinit var binding: ActivityTaskListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {

        viewModalContacts = ViewModelProvider(this)[TaskViewModel::class.java]

        taskListAdapter = TaskListAdapter(this, this)
        binding.rvContactList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvContactList.adapter = taskListAdapter



        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                taskListAdapter.filter.filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                binding.lblContactNotFound.visibility =
                    if (taskListAdapter.objList.size > 0) View.GONE else View.VISIBLE
            }

        })

        setData()

        binding.btnAdd.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnAdd -> {
                startActivity(Intent(this@TaskListActivity, AddTaskActivity::class.java))
            }

            R.id.btnDelete -> {
                lastPos = Integer.parseInt(view.tag.toString())

                viewModalContacts.deleteContact(taskListAdapter.objList[lastPos])

                setData()

                Toast.makeText(this, "delete successfully", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun setData() {

        taskListAdapter.objList = ArrayList()

        viewModalContacts.allTaskModel.observe(this) { list ->
            taskListAdapter.objList = list as ArrayList<TaskModel>
            list.let {
                // updates the list.
                taskListAdapter.addData(it)
                binding.lblContactNotFound.visibility = if (it.isNotEmpty()) View.GONE else View.VISIBLE
            }
        }

    }
}