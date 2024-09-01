package com.example.todosapp.ui.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.todosapp.R
import com.example.todosapp.data.entity.ToDos
import com.example.todosapp.databinding.TodoCardDesignBinding
import com.example.todosapp.ui.fragment.ToDoMainFragmentDirections
import com.example.todosapp.ui.viewmodel.ToDoMainViewModel
import com.example.todosapp.ui.viewmodel.ToDoUpdateViewModel
import com.example.todosapp.util.goTo
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

class ToDosAdapter(var mContext: Context, var toDosList: List<ToDos>, var viewModel: ToDoMainViewModel, var viewModelUpdate: ToDoUpdateViewModel):
    RecyclerView.Adapter<ToDosAdapter.toDosViewHolder>() {

    inner class toDosViewHolder(var view: TodoCardDesignBinding): RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): toDosViewHolder {

        val binding:TodoCardDesignBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.todo_card_design, parent, false)
        return toDosViewHolder(binding)
    }


    override fun onBindViewHolder(holder: toDosViewHolder, position: Int) {
        val systemLanguage = Locale.getDefault().language
        val todo = toDosList[position]
        val v = holder.view

        v.toDoBundle = todo

        // düzenlenecek
        if(todo.deadline == "Deadline"){
            v.textViewDeadline.text = ""
            v.textViewDeadline.visibility = View.INVISIBLE
        }else{
            when(systemLanguage){
                "tr" -> {
                    val inputFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
                    val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val date = inputFormat.parse(todo.deadline)
                    todo.deadline = outputFormat.format(date!!)
                    Log.e("tarih",todo.deadline)
                    v.textViewDeadline.text = todo.deadline
                }
                "en" -> {
                    Log.e("tarih",todo.deadline)
                    v.textViewDeadline.text = todo.deadline
                }
            }

        }

        v.cardView.setOnClickListener {
            val direction = ToDoMainFragmentDirections.toDoMainToToDoUpdate(todo = todo)
            Navigation.goTo(it,direction)
        }

        Log.e("tarih",v.textViewDeadline.text.toString())
        var isChecked = todo.done.toBoolean()

        if(isChecked){
            v.checkButton.setImageResource(R.drawable.check_circle_24)
            v.checkButton.setColorFilter(Color.GREEN)
        }else{
            v.checkButton.setImageResource(R.drawable.button_unchecked_24)
            v.checkButton.setColorFilter(Color.BLACK)
        }

        v.checkButton.setOnClickListener {
            if(isChecked){
                viewModelUpdate.updateDone(todo.id,(!isChecked).toString())
                v.checkButton.setImageResource(R.drawable.button_unchecked_24)
                v.checkButton.setColorFilter(Color.BLACK)
            }else{
                viewModelUpdate.updateDone(todo.id, (!isChecked).toString())
                v.checkButton.setImageResource(R.drawable.check_circle_24)
                v.checkButton.setColorFilter(Color.GREEN)
            }
            isChecked = !isChecked
        }

        v.buttonDelete.setOnClickListener {
            Snackbar.make(it,"${todo.name} silinsin mi?",Snackbar.LENGTH_SHORT)
                .setAction("EVET"){
                    viewModel.delete(todo.id)
                }
                .setBackgroundTint(Color.parseColor("#FF725E"))
                .setActionTextColor(Color.WHITE)
                    .show()
        }

    }

    override fun getItemCount(): Int {
        return  toDosList.size
    }

}