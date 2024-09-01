package com.example.todosapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todosapp.data.entity.ToDos
import com.example.todosapp.data.repo.ToDosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoUpdateViewModel @Inject constructor (var toDoRepo: ToDosRepository): ViewModel() {


    fun update(id: Int, name: String, deadline: String, done: String){
        CoroutineScope(Dispatchers.Main).launch {
            toDoRepo.update(id,name,deadline,done)
        }
    }

    fun updateDone(id: Int, done: String){
        CoroutineScope(Dispatchers.Main).launch {
            toDoRepo.updateDone(id,done)
        }
    }

}