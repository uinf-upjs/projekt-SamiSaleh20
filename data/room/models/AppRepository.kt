package com.example.compose_demo.data.room.models

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.Date

class AppRepository(
    private val taskDao: TaskDao,
    private val dayDao :DayDao
    //private val db : TaskDatabase
) {
    val getalldays=dayDao.getalldays()
    suspend fun newday(day: Day){
        withContext(Dispatchers.IO){
            dayDao.insert(day)
        }

        //db.dayDao().insert(day)
    }
    suspend fun newtask(task: Task){
        withContext(Dispatchers.IO){
            taskDao.insert(task)
        }
        //db.taskDao().insert(task)
    }
    suspend fun deletetask(task: Task){
        taskDao.delete(task)
        //db.taskDao().delete(task)
    }
    suspend fun gettaskbyday(date: Int) =taskDao.gettasksbydday(date)//db.taskDao().gettasksbydday(date)
    suspend fun getday(date: Date):Day?{
        return withContext(Dispatchers.IO) {
            dayDao.getdaybydate(date)
        }
    }



    suspend fun  getalltasks():Flow<List<Task>>{
        return taskDao.getalltasks()
        //return db.taskDao().getalltasks()
    }






}