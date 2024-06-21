package com.example.compose_demo.data.room.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.Date

class AppRepository(
    private val taskDao: TaskDao,
    private val dayDao :DayDao
    //private val db : TaskDatabase
) {
    //suspend fun getalldays():Int{
       // return dayDao.getalldays()
    //}

   // suspend fun  allunperfectdays():Int{
     //   return taskDao.allunperfectdays()
    //}
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
    suspend fun updatetask(task: Task){
        withContext(Dispatchers.IO){
            taskDao.update(task)
        }
    }
    suspend fun deletetask(task: Task){
        withContext(Dispatchers.IO){
            taskDao.delete(task)
        }

        //db.taskDao().delete(task)
    }
    suspend fun gettaskbyday(date: Int) =taskDao.gettasksbydday(date)//db.taskDao().gettasksbydday(date)
    suspend fun getday(date: Date):Day?{

        return dayDao.getdaybydate(date)

    }



    //val getalltasks= taskDao.getalltasks()
    suspend fun getalltasksofday(date:Int)=taskDao.getalltasksofday(date)
        //return db.taskDao().getalltasks()








}