package com.example.todoapp.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack_compose_assignment_2.domain.Todo
import com.example.jetpack_compose_assignment_2.domain.repository.TodoRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class TodoListState {
    object Loading : TodoListState()
    data class Success(val todos: List<Todo>) : TodoListState()
    data class Error(val message: String) : TodoListState()
}

class TodoListViewModel(
    private val repository: TodoRepository
) : ViewModel() {

    private val _state = MutableStateFlow<TodoListState>(TodoListState.Loading)
    val state: StateFlow<TodoListState> = _state

    init {
        loadTodos()
    }

    fun loadTodos() {
        viewModelScope.launch {
            _state.value = TodoListState.Loading
            try {
                // Try to fetch from network and update cache
                repository.refreshTodos()
            } catch (e: Exception) {
                // Log error and fallback to cached data
                _state.value = TodoListState.Error(" ${e.message ?: "Unknown error"} — showing cached data if available")
            }

            // Collect cached data regardless of network result
            try {
                repository.getTodos().collect { todos ->
                    if (todos.isNotEmpty()) {
                        _state.value = TodoListState.Success(todos)
                    } else {
                        _state.value = TodoListState.Error("No todos found in local cache.")
                    }
                }
            } catch (e: Exception) {
                _state.value = TodoListState.Error("Failed to load data from cache: ${e.message ?: "Unknown error"}")
            }
        }
    }
}

