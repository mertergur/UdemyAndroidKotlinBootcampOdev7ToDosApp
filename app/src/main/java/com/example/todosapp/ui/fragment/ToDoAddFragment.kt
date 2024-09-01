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
import androidx.navigation.Navigation
import com.example.todosapp.R
import com.example.todosapp.databinding.FragmentToDoAddBinding
import com.example.todosapp.ui.viewmodel.ToDoAddViewModel
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
class ToDoAddFragment : Fragment() {
    private lateinit var binding: FragmentToDoAddBinding
    private lateinit var viewModel: ToDoAddViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_to_do_add, container, false)
        binding.toDoAddFragmemnt = this
        binding.toDoAddDeadline = "Deadline"

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: ToDoAddViewModel by viewModels()
        viewModel = tempViewModel
    }


    fun buttonSave(name: String, deadline: String, it: View){
        if(!binding.editTextViewToDoContent.text.toString().isNullOrBlank()) {
            val systemLanguage = Locale.getDefault().language
            when (systemLanguage) {
                "tr" -> {
                    if (deadline != "Deadline") {
                        var deadlineDateTR = deadline
                        val inputFormat =
                            java.text.SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        val outputFormat =
                            java.text.SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
                        val date = inputFormat.parse(deadlineDateTR)
                        val formattedTarih = outputFormat.format(date!!)
                        deadlineDateTR = formattedTarih
                        viewModel.save(name, deadlineDateTR)
                    } else
                        viewModel.save(name, deadline)
                }
                "en" -> {
                    viewModel.save(name, deadline)
                }
            }
        }else {
            Snackbar.make(it, "Başlık boş olamaz.", Snackbar.LENGTH_SHORT)
                .setBackgroundTint(Color.parseColor("#FF725E"))
                .setActionTextColor(Color.WHITE)
                .show()
        }
        Navigation.goTo(it,R.id.toDoAddFragment_to_toDoMainFragment)
    }

    fun toDoAddButtonDatePicker(){
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
                    binding.textViewDeadline.text = date
                }
                "en" -> {
                    val df = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
                    val date = df.format(it)
                    binding.textViewDeadline.text = date
                }
            }
        }
    }
}