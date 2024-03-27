package com.example.taskmanagement.db

import androidx.lifecycle.LiveData
import com.example.taskmanagement.model.TaskModel

class EventRepository(private val taskDao: TaskDao) {

    fun getAllContacts(): LiveData<List<TaskModel>> = taskDao.getAllContacts()

    suspend fun insertEvent(taskModel: TaskModel) {
        taskDao.insert(taskModel)
    }

    suspend fun deleteContact(taskModel: TaskModel) = taskDao.deleteContact(taskModel)

/*    suspend fun clearEvent() = contactsDao.c()*/

  /*  suspend fun deleteEvent(event: Category) {
        eventDao.deleteEvent(event)
    }

    suspend fun updateEvent(event: Category) {
        eventDao.updateEvent(event)
    }

    suspend fun deleteEventById(id: Int) = eventDao.deleteEventById(id)

    suspend fun clearEvent() = eventDao.clearEvent()*/
}