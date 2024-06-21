package com.example.compose_demo


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.compose_demo.data.room.models.AppRepository
import com.example.compose_demo.data.room.models.DateConverter
import com.example.compose_demo.data.room.models.Day
import com.example.compose_demo.data.room.models.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class MainViewModel(private val repository: AppRepository) : ViewModel() {
     var name : String by mutableStateOf("")
     var date : String  by mutableStateOf("")
     val calendar = Calendar.getInstance()
     val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
     val dateConverter = DateConverter()
     var perfectdays=0

     private val _tasksLiveData = MutableLiveData<List<Task>>()


     val tasksLiveData: LiveData<List<Task>> get() = _tasksLiveData

     init {
          date = dateFormat.format(calendar.time)
     }
     class MainViewModellFactory(private val repository: AppRepository) : ViewModelProvider.Factory {
          override fun <T : ViewModel> create(modelClass: Class<T>): T {
               if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                    return MainViewModel(repository) as T
               }
               throw IllegalArgumentException("Unknown ViewModel class")
          }

     }

     fun newday(day: Day){
          viewModelScope.launch { repository.newday(day) }
     }
     fun newtask(task: Task){
          viewModelScope.launch { repository.newtask(task) }
     }
     fun updatetask(task: Task){
          viewModelScope.launch { repository.updatetask(task) }
     }
     fun deletetask(task: Task){
          viewModelScope.launch { repository.deletetask(task) }
     }
     fun gettaskbyday(date:Int){
         viewModelScope.launch { repository.gettaskbyday(date) }
     }

     fun getday(value: String, callback: (Day?) -> Unit) {
          viewModelScope.launch(Dispatchers.IO) {
               val taskDate = dateConverter.toDateFromString(date)
               val result = repository.getday(taskDate)
               withContext(Dispatchers.Main) {
                    callback(result)
               }

          }
     }
     fun alltasksofday(date: Int){
          viewModelScope.launch(Dispatchers.IO) {
               repository.getalltasksofday(date).collect { tasks ->
                    _tasksLiveData.postValue(tasks)

               }
          }
     }
     fun allperfectdays(){
          viewModelScope.launch {
               //var getalldays=repository.getalldays()
               //var allunperfectdays=repository.allunperfectdays()
               //perfectdays=getalldays-allunperfectdays

          }
     }


     //val alltasks=repository.getalltasks.asLiveData()
}