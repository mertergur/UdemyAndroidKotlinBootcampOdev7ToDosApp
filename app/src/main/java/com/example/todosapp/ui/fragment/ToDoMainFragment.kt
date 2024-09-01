package com.example.todosapp.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import com.example.todosapp.R
import com.example.todosapp.data.entity.ToDos
import com.example.todosapp.databinding.FragmentToDoMainBinding
import com.example.todosapp.ui.adapter.ToDosAdapter
import com.example.todosapp.ui.viewmodel.ToDoMainViewModel
import com.example.todosapp.ui.viewmodel.ToDoUpdateViewModel
import com.example.todosapp.util.goTo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ToDoMainFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentToDoMainBinding
    private lateinit var viewModel: ToDoMainViewModel
    private lateinit var viewModelUpdate: ToDoUpdateViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_to_do_main, container, false)
        binding.toDoMainFragment = this
        binding.toDoMainToolbarTitle = ""
        binding.toDoMainToolbarText = "To-Do"
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)


        viewModel.toDosList.observe(viewLifecycleOwner){
            val toDosAdapter = ToDosAdapter(requireContext(),it,viewModel,viewModelUpdate)
            binding.toDosAdapter = toDosAdapter
        }


        requireActivity().addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)

                val item = menu.findItem(R.id.action_search)
                val searchView = item.actionView as SearchView

                searchView.setOnQueryTextListener(this@ToDoMainFragment)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: ToDoMainViewModel by viewModels()
        val tempViewModelUpdate: ToDoUpdateViewModel by viewModels()
        viewModelUpdate = tempViewModelUpdate
        viewModel = tempViewModel
    }

    fun fabOnClick(it: View){
        Navigation.goTo(it,R.id.toDoMain_to_toDoAdd)
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        viewModel.search(query)
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        viewModel.search(newText)
        return true
    }

    override fun onResume() {
        super.onResume()
        viewModel.toDosLoad()
    }
}