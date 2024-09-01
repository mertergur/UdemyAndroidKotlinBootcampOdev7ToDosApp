package com.example.todosapp.data.datasource

import android.util.Log
import com.example.todosapp.data.entity.ToDos
import com.example.todosapp.room.ToDosDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ToDosDataSource(var tdDao: ToDosDao) {

    suspend fun toDosLoad(): List<ToDos> =
        withContext(Dispatchers.IO) {
            return@withContext tdDao.loadAllToDo()

        }

    suspend fun search(searchKeyWord: String): List<ToDos> =
        withContext(Dispatchers.IO) {

            return@withContext tdDao.search(searchKeyWord)

        }

    suspend fun save(name: String, deadline: String) {
        val newToDo = ToDos(0, name, deadline, "false")
        tdDao.save(newToDo)
    }

    suspend fun update(id: Int, name: String, deadline: String, done: String) {
        val updateToDo = ToDos(id, name, deadline, done)
        tdDao.update(updateToDo)
    }

    suspend fun updateDone(id: Int, done: String) {
        val updateToDo = ToDos(id, "", "", done)
        tdDao.updateDone(id,done)
    }


    suspend fun delete(id: Int) {
        val deleteToDo = ToDos(id, "", "", "")
        tdDao.delete(deleteToDo)
    }

    suspend fun doneCount():Int{
        return tdDao.doneCount()
    }
}