package com.example.jetpack_compose_assignment_2.domain.repository
import com.example.jetpack_compose_assignment_2.domain.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getTodos(): Flow<List<Todo>>
    suspend fun getTodoById(id: Int): Todo?
    suspend fun refreshTodos()
}