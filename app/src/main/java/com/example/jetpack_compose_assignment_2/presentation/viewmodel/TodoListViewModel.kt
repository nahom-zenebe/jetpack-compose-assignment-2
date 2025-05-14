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
        fetchTodos()
    }

    private fun fetchTodos() {
        viewModelScope.launch {
            try {
                repository.getTodos().collect { todos ->
                    _state.value = TodoListState.Success(todos)
                }
            } catch (e: Exception) {
                _state.value = TodoListState.Error("Failed to load todos")
            }
        }
    }
}
