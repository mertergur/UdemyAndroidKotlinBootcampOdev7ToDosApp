package com.example.todosapp.util

import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.example.todosapp.R

fun Navigation.goTo(it: View,id: Int){
    findNavController(it).navigate(id)
}

fun Navigation.goTo(it: View,id: NavDirections){
    findNavController(it).navigate(id)
}