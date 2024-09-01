package com.example.todosapp.data.repo

import com.example.todosapp.data.datasource.ToDosDataSource
import com.example.todosapp.data.entity.ToDos

class ToDosRepository(var tdds: ToDosDataSource) {


    suspend fun save(name: String, deadline: String) = tdds.save(name,deadline)

    suspend fun update(id: Int, name: String, deadline: String,done: String) = tdds.update(id, name, deadline,done)

    suspend fun updateDone(id: Int,done: String) = tdds.updateDone(id, done)

    suspend fun delete(id:Int) = tdds.delete(id)

    suspend fun toDosLoad(): List<ToDos> = tdds.toDosLoad()

    suspend fun search(searchKeyWord: String): List<ToDos> = tdds.search(searchKeyWord)

    suspend fun doneCount(): Int = tdds.doneCount()

}