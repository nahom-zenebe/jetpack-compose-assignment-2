package com.example.jetpack_compose_assignment_2

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetpack_compose_assignment_2.data.local.TodoDatabase
import com.example.jetpack_compose_assignment_2.data.remote.RetrofitInstance
import com.example.jetpack_compose_assignment_2.domain.repository.TodoRepositoryImpl
import com.example.jetpack_compose_assignment_2.ui.theme.Jetpackcomposeassignment2Theme
import com.example.todoapp.presentation.navigation.AppNavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val repository by lazy {
            val database = TodoDatabase.getInstance(applicationContext)
            TodoRepositoryImpl(
                api = RetrofitInstance.api,
                dao = database.todoDao()
            )
        }
        setContent {
            Jetpackcomposeassignment2Theme {
                    AppNavGraph(repository = repository)
                }
            }
        }
    }



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Jetpackcomposeassignment2Theme {

    }
}