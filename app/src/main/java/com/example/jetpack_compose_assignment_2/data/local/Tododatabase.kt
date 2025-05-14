package com.example.jetpack_compose_assignment_2.data.local

import TodoDao
import TodoEntity
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TodoEntity::class], version = 1)
abstract  class Tododatabase:RoomDatabase() {
    abstract fun todoDao(): TodoDao

}