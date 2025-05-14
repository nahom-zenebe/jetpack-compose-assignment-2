package com.example.todoapp.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetpack_compose_assignment_2.domain.Todo
import com.example.jetpack_compose_assignment_2.domain.repository.TodoRepository
import com.example.todoapp.presentation.detail.TodoDetailViewModel


class TodoDetailViewModelFactory(
    private val repository: TodoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TodoDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


@Composable
fun TodoListScreen(
    viewModel: TodoListViewModel,
    onTodoClick: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()

    when (state) {
        is TodoListState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is TodoListState.Error -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = (state as TodoListState.Error).message)
            }
        }

        is TodoListState.Success -> {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items((state as TodoListState.Success).todos) { todo ->
                    TodoItem(todo = todo) {
                        onTodoClick(todo.id)
                    }
                }
            }
        }
    }
}

@Composable
fun TodoItem(todo: Todo, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(todo.title, style = MaterialTheme.typography.titleMedium)
            Text(if (todo.completed) " Completed" else " Not Completed")
        }
    }
}
