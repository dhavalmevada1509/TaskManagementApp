package com.example.taskmanagement.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.taskmanagement.db.EventRepository
import com.example.taskmanagement.db.TaskDatabase
import com.example.taskmanagement.model.TaskModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(
    application: Application
) : AndroidViewModel(application) {


    val allTaskModel: LiveData<List<TaskModel>>
    val repository: EventRepository

    // initialize dao, repository and all events
    init {
        val dao = TaskDatabase.getDatabase(application).getContactDao()
        repository = EventRepository(dao)
        allTaskModel = repository.getAllContacts()
    }

    fun insertContacts(taskModel: TaskModel) =
        viewModelScope.launch(Dispatchers.IO) { repository.insertEvent(taskModel) }

    fun deleteContact(taskModel: TaskModel) =
        viewModelScope.launch(Dispatchers.IO) { repository.deleteContact(taskModel) }

}