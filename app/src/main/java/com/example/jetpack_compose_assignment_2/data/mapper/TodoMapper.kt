package com.example.todoapp.data.mapper
import com.example.jetpack_compose_assignment_2.data.local.TodoEntity
import com.example.jetpack_compose_assignment_2.domain.Todo


fun TodoEntity.toDomain(): Todo {
    return Todo(
        id = id,
        userId = userId,
        title = title,
        completed = completed
    )
}

fun Todo.toEntity(): TodoEntity {
    return TodoEntity(
        id = id,
        userId = userId,
        title = title,
        completed = completed
    )
}
