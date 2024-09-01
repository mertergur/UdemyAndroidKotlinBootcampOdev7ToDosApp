package com.example.todosapp.ui.viewmodel

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
class ToDoAddViewModel @Inject constructor (var toDoRepo: ToDosRepository): ViewModel() {
     fun save(name: String, deadline: String){
         CoroutineScope(Dispatchers.Main).launch {
             toDoRepo.save(name,deadline)
         }
     }

}