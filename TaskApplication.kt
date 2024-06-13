package com.example.compose_demo

import android.app.Application
import com.example.compose_demo.data.room.models.AppRepository
import com.example.compose_demo.data.room.models.TaskDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class TaskApplication:Application() {
    val aplicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { TaskDatabase.getDatabase(this) }
    val repository by lazy { AppRepository(database.taskDao(),database.dayDao()) }

}