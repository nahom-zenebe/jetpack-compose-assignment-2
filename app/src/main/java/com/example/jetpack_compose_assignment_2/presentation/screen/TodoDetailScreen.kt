import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDetailScreen(
    todoId: Int,
    viewModel: TodoDetailViewModel,
    onBack: () -> Unit
) {
    val todo by viewModel.todo.collectAsState()

    LaunchedEffect(todoId) {
        viewModel.loadTodoById(todoId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Todo Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            todo?.let {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("ID: ${it.id}")
                    Text("Title: ${it.title}", style = MaterialTheme.typography.titleLarge)
                    Text("Status: ${if (it.completed) " Completed" else " Not Completed"}")
                    Text("User ID: ${it.userId}")
                }
            } ?: Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}
