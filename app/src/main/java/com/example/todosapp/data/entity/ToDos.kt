package com.example.todosapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = "toDos")
data class ToDos(@PrimaryKey(autoGenerate = true)
                 @ColumnInfo(name = "id") @NotNull var id: Int,
                 @ColumnInfo(name = "name") @NotNull var name:String,
                 @ColumnInfo(name = "deadline") @NotNull var deadline:String,
                 @ColumnInfo(name = "done") @NotNull var done: String
): Serializable {
}