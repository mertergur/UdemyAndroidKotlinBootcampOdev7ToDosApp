package com.example.todosapp.ui.viewmodel

import android.util.Log
import android.view.View
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
class ToDoMainViewModel @Inject constructor (var toDoRepo: ToDosRepository): ViewModel() {
    var toDosList = MutableLiveData<List<ToDos>>()
    var toDoCount = MutableLiveData<Int>()

    init {
        toDosLoad()
    }

    fun delete(id: Int){
        CoroutineScope(Dispatchers.Main).launch {
            toDoRepo.delete(id)
            toDosLoad()
        }
    }

    fun toDosLoad(){
        CoroutineScope(Dispatchers.Main).launch {
            toDosList.value = toDoRepo.toDosLoad()
        }
    }

    fun search(searchKeyWord: String){
        CoroutineScope(Dispatchers.Main).launch {
            toDosList.value = toDoRepo.search(searchKeyWord)
        }

    }

}