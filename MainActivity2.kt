package com.example.compose_demo

import android.app.Application
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.compose_demo.data.room.models.AppRepository
import com.example.compose_demo.data.room.models.DateConverter
import com.example.compose_demo.data.room.models.Day
import com.example.compose_demo.data.room.models.Task
import com.example.compose_demo.data.room.models.TaskDatabase
import com.example.compose_demo.ui.theme.Compose_demoTheme
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class MainActivity2 : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels {
        MainViewModel.MainViewModellFactory((application as TaskApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Compose_demoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainActivity2Screen(viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainActivity2Screen(viewmodel:MainViewModel) {
    //val nameState = remember { mutableStateOf("") }
   // val dateState = remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val steps =0
    val all_steps =0
    val all_done_tasks=0
    val all_tasks=0
    val perfect_days=0

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "TO_DO") },
                navigationIcon = {
                    IconButton(onClick = { Toast.makeText(context, "TO_DO", Toast.LENGTH_SHORT).show() }) {
                        // Icon(painter = painterResource(id = R.drawable.apple), contentDescription = "to do")
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
        content = { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "ADD NEW TASK",
                            modifier = Modifier.padding(50.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 30.sp)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically

                    ){
                        Text(
                            text = "Name :",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 15.sp),
                            modifier = Modifier.padding(start = 16.dp)
                        )
                        OutlinedTextField(value = viewmodel.name,
                            onValueChange = { viewmodel.name = it },label ={
                            Text(text = "name of task")
                        }, modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )


                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically

                    ){
                        Text(
                            text = "Date :",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 15.sp),
                            modifier = Modifier.padding(start = 16.dp)
                        )
                        OutlinedTextField(value = viewmodel.date,
                            onValueChange = { viewmodel.date = it },label ={
                            Text(text = "yyyy-MM-dd")
                        },modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            )
                        )


                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically

                    ){
                        Text(
                            text = "Notifications :",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 15.sp),
                            modifier = Modifier.padding(start = 16.dp)
                        )
                        Switch(
                            checked = isChecked,
                            onCheckedChange = { isChecked = it },
                            //modifier = Modifier.weight(1f)

                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Button( onClick = {
                            //val newtask=Task(name = viewmodel.name, done = false, dayId=0,id=0)
                            //viewmodel.newtask(newtask)
                            viewmodel.getalldays

                            val day=viewmodel.getday(viewmodel.date)
                            if (day!= null) {
                                val newtask=Task(name = viewmodel.name, done = false, dayId=day.id,id=0)
                                viewmodel.newtask(newtask)
                                viewmodel.name=""
                                val calendar = Calendar.getInstance()
                                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                viewmodel.date=dateFormat.format(calendar.time)
                                //viewmodel.date="1111"
                            } else {
                                val date= viewmodel.dateConverter.toDateFromString(viewmodel.date)
                                //val day=viewmodel.newday(Day(id=0,date=date,steps=0))
                                val day=viewmodel.getday(viewmodel.date)
                                if (day!= null) {
                                    val newtask=Task(name = viewmodel.name, done = false, dayId=day.id,id=0)
                                    viewmodel.newtask(newtask)
                                    viewmodel.name=""
                                    val calendar = Calendar.getInstance()
                                    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                    viewmodel.date=dateFormat.format(calendar.time)
                                    //viewmodel.date="1111"
                                }
                            }














                        },

                            colors = ButtonDefaults.buttonColors(
                                Color.Green
                            )

                        ){
                                Text(text = "ADD", color = Color.Black)
                            }



                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically

                    ){
                        Spacer(modifier = Modifier.weight(1f))
                        Button( onClick = {  },



                            colors = ButtonDefaults.buttonColors(
                                Color.Green
                            )

                        ){
                            Text(text = "add for next 5 days", color = Color.Black)
                        }

                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically

                    ){
                        Spacer(modifier = Modifier.weight(1f))
                        Button( onClick = { },



                            colors = ButtonDefaults.buttonColors(
                                Color.Green
                            )

                        ){
                            Text(text = "add for whole month", color = Color.Black)
                        }

                    }


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
                                    Text(text = "$perfect_days")

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

@Preview(showBackground = true)
@Composable
fun MainActivity2ScreenPreview() {
    Compose_demoTheme {

        //MainActivity2Screen(viewModel)
    }
}