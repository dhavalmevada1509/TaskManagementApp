package com.example.taskmanagement.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.taskmanagement.model.TaskModel

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(taskModel: TaskModel)

    @Delete
    fun deleteContact(taskModel: TaskModel)

    @Query("select * from contacts ")
    fun getAllContacts(): LiveData<List<TaskModel>>


}