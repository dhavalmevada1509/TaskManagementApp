package com.example.taskmanagement.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")

class TaskModel (

    @ColumnInfo(name = "project_name")
    var projectName: String = "",

    @ColumnInfo(name = "project_desc")
    var projectDesc: String = "",

    @ColumnInfo(name = "date")
    var date: String = "",

){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null
}