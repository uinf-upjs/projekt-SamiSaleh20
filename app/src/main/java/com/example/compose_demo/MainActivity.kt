package com.example.compose_demo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.example.compose_demo.data.room.models.Task
import com.example.compose_demo.ui.theme.Compose_demoTheme
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import com.example.compose_demo.data.room.models.Day
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context

data class Course(
    val grade:String,
    val code:String,
    val name:String
)


class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels {
        MainViewModel.MainViewModellFactory((application as TaskApplication).repository)
    }
    private var list: List<Task> = emptyList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Compose_demoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyAppScaffold(viewModel)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppScaffold(viewModel:MainViewModel) {

    val context = LocalContext.current
    val steps =0
    val all_steps =0
    val all_done_tasks=0
    val all_tasks=0
    val perfect_days=0
    var curdate=Int.MAX_VALUE
    var showDialog by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        val day=viewModel.getday(viewModel.date){day->
            if (day!= null) {
                curdate=day.id
                viewModel.alltasksofday(day.id)
            }

        }
    }



    Scaffold(

        topBar = {
            TopAppBar(
                title = { Text(text = "TO_DO") },
                navigationIcon = {
                    IconButton(onClick = { Toast.makeText(context, "TO_DO", Toast.LENGTH_SHORT).show() }) {
                        // Icon(painter = painterResource(id = "R.drawable.apple), contentDescription = "to do")
                    }
                },
                actions = {
                    IconButton(onClick = { showDialog=true }) {
                        Icon(imageVector = Icons.Filled.Person, contentDescription = "Menu", tint = Color.Black)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Green
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {val intent = Intent(context, MainActivity2::class.java)
                context.startActivity(intent)}, containerColor = Color.Green) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
            }
        },
        content = { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Column (
                    modifier = Modifier.fillMaxWidth()){
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),verticalAlignment = Alignment.CenterVertically) {
                        OutlinedTextField(value = viewModel.date,
                            onValueChange = {  viewModel.date = it},label ={
                                Text(text = "yyyy-MM-dd")
                            },modifier =Modifier.padding(16.dp),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            )
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Button(onClick = {

                            val day=viewModel.getday(viewModel.date){day->
                                if (day!= null) {
                                    curdate=day.id
                                    viewModel.alltasksofday(day.id)
                                }else{
                                    viewModel.alltasksofday(Int.MAX_VALUE)
                                }

                            }
                            },
                            colors = ButtonDefaults.buttonColors(
                                Color.Green
                            )
                        ) {
                            Text(text = "Search", color = Color.Black)

                        }

                    }
                    taskView(viewModel = viewModel)


                }

                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text(text = "My stats") },
                        text = {
                            Column {
                                Row {
                                    Text(text = "Today steps: ")
                                    Text("$steps")

                                }
                                Row {
                                    Text(text = "All steps: ")
                                    Text(text = "$all_steps")

                                }
                                Row {
                                    Text(text = "All done tasks: ")
                                    Text(text = "$all_done_tasks")

                                }
                                Row {
                                    Text(text = "All tasks")
                                    Text(text = "$all_tasks")

                                }
                                Row {
                                    Text(text = "Perfect days: ")
                                    Text(text = "${viewModel.perfectdays}")

                                }

                            }
                        },



                        confirmButton = {
                            Button(
                                onClick = { showDialog = false },
                                colors = ButtonDefaults.buttonColors(
                                    Color.Green
                                )
                            ) {
                                Text(text = "Close")
                            }
                        }
                    )
                }

            }
        }
    )
}
@Composable
fun oneTask(task:Task,viewModel:MainViewModel){
    val context= LocalContext.current
    Surface(color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)) {
        Column(modifier= Modifier
            .padding((24.dp))
            .fillMaxWidth()) {
            Row {
                Text(text = task.name,  modifier = Modifier.padding(16.dp),)
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        viewModel.deletetask(task = task)
                        Toast.makeText(context, "task was deleted succesfully", Toast.LENGTH_SHORT).show()
                              },
                    colors=ButtonDefaults.buttonColors(Color.Red)) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "")
                }
                Button(
                    onClick = {
                        Toast.makeText(context, "task completed successfully", Toast.LENGTH_SHORT).show()
                        val updatetask=Task(task.id,task.name,true,task.dayId)
                        viewModel.updatetask(updatetask)
                        },
                    colors=ButtonDefaults.buttonColors(Color.Green)) {
                    Icon(imageVector = Icons.Filled.Check, contentDescription = "")

                }
            }

        }

    }


}
@Composable
fun taskView(viewModel:MainViewModel){
    val tasky by viewModel.tasksLiveData.observeAsState(initial = emptyList())
    LazyColumn (modifier= Modifier.padding(vertical = 4.dp)){
        items(items=tasky){task->
            oneTask(task = task,viewModel)

        }

    }

}

        @Preview(showBackground = true)
@Composable
fun defaultPreview(){
    Compose_demoTheme{
        val task1=Task(name = "zjest kolac", dayId = 2,done=false,id=5)
        val task2=Task(name = "dnes je pekne", dayId = 2,done=false,id=5)
        val task3=Task(name = "zajtra bude pekne ", dayId = 2,done=false,id=5)
        val tasks: List<Task> = listOf(task1,task2,task3)
        //taskView(tasks =tasks )
        //oneTask(task = task1)

    }
}









