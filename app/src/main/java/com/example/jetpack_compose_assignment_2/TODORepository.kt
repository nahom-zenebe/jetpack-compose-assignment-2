package com.example.jetpack_compose_assignment_2

import com.example.jetpack_compose_assignment_2.domain.model.TODO

interface TODORepository{

suspend fun getallItem():List<TODO>
suspend fun additem(title:String,completed:Boolean)
suspend fun deleteitem(id:String)


}