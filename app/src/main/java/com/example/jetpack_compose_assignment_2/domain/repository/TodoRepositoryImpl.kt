package com.example.jetpack_compose_assignment_2.domain.repository

import TodoDao
import com.example.jetpack_compose_assignment_2.data.remote.ApiService
import com.example.jetpack_compose_assignment_2.domain.Todo
import kotlinx.coroutines.flow.flow
import com.example.todoapp.data.mapper.toDomain
import com.example.todoapp.data.mapper.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class TodoRepositoryImpl(private val api: ApiService, private val dao: TodoDao):TodoRepository {

    override fun getTodos(): Flow<List<Todo>> = flow {
        val cached = dao.getTodos().map { it.map { entity -> entity.toDomain() } }
        emitAll(cached)

        try {
            val remoteTodos = api.getTodos()
            dao.insertTodos(remoteTodos.map { it.toEntity() })
        } catch (e: Exception) {

        }
    }

    override suspend fun getTodoById(id: Int): Todo? {
        return dao.getTodoById(id)?.toDomain()
    }
}

