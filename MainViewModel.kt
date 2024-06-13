package com.example.compose_demo


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.compose_demo.data.room.models.AppRepository
import com.example.compose_demo.data.room.models.DateConverter
import com.example.compose_demo.data.room.models.Day
import com.example.compose_demo.data.room.models.Task
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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
     val getalldays=repository.getalldays.asLiveData()

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
     fun deletetask(task: Task){
          viewModelScope.launch { repository.deletetask(task) }
     }
     fun gettaskbyday(date:Int){
         viewModelScope.launch { repository.gettaskbyday(date) }
     }
     fun getday(date: String):Day?{
          return runBlocking {
               viewModelScope.async {
                    val taskDate = dateConverter.toDateFromString(date)
                    val day = repository.getday(taskDate)
                    return@async day
               }.await()
          }
     }
     fun getalltasks(){
        viewModelScope.launch {  repository.getalltasks() }
     }
}