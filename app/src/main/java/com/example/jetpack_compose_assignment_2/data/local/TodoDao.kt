package com.example.jetpack_compose_assignment_2.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao{
    @Query("SELECT * FROM todos")
    fun getTodos():Flow<List<TodoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodos(todos:List<TodoEntity>)

    @Query("SELECT * FROM todos WHERE id = :todoId")
    suspend fun getTodoById(todoId: Int): TodoEntity

}