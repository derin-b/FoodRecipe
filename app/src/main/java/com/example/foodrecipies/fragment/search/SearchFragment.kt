package com.example.foodrecipies.fragment.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodrecipies.databinding.FragmentSearchBinding
import com.example.foodrecipies.fragment.favourites.MealsAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchViewModel: SearchViewModel

    private val mealsAdapter by lazy { MealsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val application = requireNotNull(this.activity).application
        val searchViewModelFactory = SearchViewModel.SearchViewModelFactory(application)

        searchViewModel = ViewModelProvider(this,searchViewModelFactory).get()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()

        binding.icSearch.setOnClickListener {
            searchMeals()
        }
        observeSearchMeals()

        var searchJob: Job? = null
        binding.edSearch.addTextChangedListener{
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500)
                searchViewModel.searchMeal(it.toString())
            }
        }
    }

    private fun observeSearchMeals() {
        searchViewModel.observeSearchMeal().observe(viewLifecycleOwner, Observer {
            mealsAdapter.differ.submitList(it)
        })
    }

    private fun searchMeals() {
        val searchQuery = binding.edSearch.text.toString()
        if (searchQuery.isNotEmpty()){
            searchViewModel.searchMeal(searchQuery)
        }
    }

    private fun prepareRecyclerView() {
        binding.rvSearch.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = mealsAdapter
        }
    }

}