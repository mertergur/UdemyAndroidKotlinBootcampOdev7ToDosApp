package com.example.todosapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todosapp.data.entity.ToDos

@Dao
interface ToDosDao {
    @Query("SELECT * FROM toDos ORDER BY deadline ASC")
    suspend fun loadAllToDo(): List<ToDos>

    @Insert
    suspend fun save(toDo: ToDos)

    @Update
    suspend fun update(toDo: ToDos)

    @Query("UPDATE toDos SET done = :done WHERE id= :id")
    suspend fun updateDone(id:Int, done: String)

    @Delete
    suspend fun delete(toDo: ToDos)

    @Query("SELECT * FROM toDos WHERE name like '%'||:searchKeyWord||'%'")
    suspend fun search(searchKeyWord: String): List<ToDos>

    @Query("Select Count(*) FROM toDos Where done = 'true'")
    suspend fun doneCount(): Int
}