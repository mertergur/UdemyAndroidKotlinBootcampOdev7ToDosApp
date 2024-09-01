package com.example.todosapp.ui.fragment

import android.content.DialogInterface
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todosapp.R
import com.example.todosapp.data.entity.ToDos
import com.example.todosapp.databinding.FragmentToDoUpdateBinding
import com.example.todosapp.ui.viewmodel.ToDoMainViewModel
import com.example.todosapp.ui.viewmodel.ToDoUpdateViewModel
import com.example.todosapp.util.goTo
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
@AndroidEntryPoint
class ToDoUpdateFragment : Fragment() {

    private lateinit var binding: FragmentToDoUpdateBinding
    private lateinit var viewModel: ToDoUpdateViewModel
    private lateinit var viewModelMain: ToDoMainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_to_do_update, container, false)
        binding.toDoUpdateFragment = this

        val bundle: ToDoUpdateFragmentArgs by navArgs()
        val argsToDo = bundle.todo
        binding.toDoBundle = argsToDo

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: ToDoUpdateViewModel by viewModels()
        viewModel = tempViewModel
    }

    fun buttonUpdate(id: Int, name: String, deadline: String,done: String,it: View){
        Log.e("todo",done)
        if(!binding.editTextToDoContentUpdate.text.toString().isNullOrBlank()) {
            val systemLanguage = Locale.getDefault().language
            when (systemLanguage) {
                "tr" -> {
                    var deadlineDateTR = deadline
                    val inputFormat = java.text.SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val outputFormat = java.text.SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
                    val date = inputFormat.parse(deadlineDateTR)
                    val formattedTarih = outputFormat.format(date!!)
                    deadlineDateTR = formattedTarih
                    viewModel.update(id, name, deadlineDateTR, done)
                }

                "en" -> {
                    viewModel.update(id, name, deadline, done)
                }
            }
        }else{
            Snackbar.make(it, "Başlık boş olamaz.", Snackbar.LENGTH_SHORT)
                .setBackgroundTint(Color.parseColor("#FF725E"))
                .setActionTextColor(Color.WHITE)
                .show()
        }

        Navigation.goTo(it,R.id.toDoUpdateFragment_to_toDoMainFragment)

    }

    fun toDoUpdateButtonDatePicker(){
        val dp = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Date")
            .build()
        dp.show(parentFragmentManager,"datePicker")
        dp.addOnPositiveButtonClickListener {

            val systemLanguage = Locale.getDefault().language
            when(systemLanguage){
                "tr" -> {
                    val df = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val date = df.format(it)
                    binding.textViewDeadlineUpdate.text = date
                }
                "en" -> {
                    val df = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
                    val date = df.format(it)
                    binding.textViewDeadlineUpdate.text = date
                }
            }
        }
    }


}