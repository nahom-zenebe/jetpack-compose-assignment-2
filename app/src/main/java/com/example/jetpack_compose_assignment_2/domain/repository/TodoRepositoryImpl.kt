package com.example.jetpack_compose_assignment_2.domain.repository


import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.example.jetpack_compose_assignment_2.data.local.TodoDao
import com.example.jetpack_compose_assignment_2.data.remote.ApiService
import com.example.jetpack_compose_assignment_2.domain.Todo
import kotlinx.coroutines.flow.flow
import com.example.todoapp.data.mapper.toDomain
import com.example.todoapp.data.mapper.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.io.IOException

class TodoRepositoryImpl(
    private val api: ApiService,
    private val dao: TodoDao
) : TodoRepository {

    override fun getTodos(): Flow<List<Todo>> = dao.getTodos().map { list ->
        list.map { it.toDomain() }
    }

    override suspend fun getTodoById(id: Int): Todo? {
        return dao.getTodoById(id)?.toDomain()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun refreshTodos() {
        try {
            val remoteTodos = api.getTodos()
            dao.insertTodos(remoteTodos.map { it.toEntity() })
        } catch (e: IOException) {

            Log.e("TodoRepository", "Network error", e)
            throw Exception("Unable to connect. Please check your internet connection.")
        } catch (e: HttpException) {

            Log.e("TodoRepository", "Server error", e)

        } catch (e: Exception) {

            Log.e("TodoRepository", "Unexpected error", e)
            throw Exception("An unexpected error occurred.")
        }
    }
}
