package com.example.compose_demo

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract.Colors
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_demo.ui.theme.Compose_demoTheme
import com.example.compose_demo.ui.theme.Typography

data class Course(
    val grade:String,
    val code:String,
    val name:String
)


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Compose_demoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyAppScaffold()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppScaffold() {
    val context = LocalContext.current
    val steps =0
    val all_steps =0
    val all_done_tasks=0
    val all_tasks=0
    val perfect_days=0
    var showDialog by remember { mutableStateOf(false) }
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
                Text(
                    text = "Hello, World!",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    )
}
@Preview
@Composable
fun TopAppBarPrewiew(){
    Compose_demoTheme{
        MyAppScaffold()
    }
}





