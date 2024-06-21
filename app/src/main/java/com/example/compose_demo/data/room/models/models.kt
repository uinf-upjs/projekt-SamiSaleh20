package com.example.compose_demo.data.room.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Days")
data class Day(
    @ColumnInfo(name = "day_id")
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val date: Date,
    val steps: Int


)
@Entity(tableName = "Tasks")
data class Task(
    @ColumnInfo(name = "task_id")
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val name : String,
    val done : Boolean,
    val dayId : Int
)