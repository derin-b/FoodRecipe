package com.example.foodrecipies.fragment.catergories

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodrecipies.activity.categoryMeal.CategoryMealsActivity
import com.example.foodrecipies.databinding.FragmentCategoriesBinding
import com.example.foodrecipies.fragment.home.HomeFragment
import com.example.foodrecipies.model.category.Category

class CategoriesFragment : Fragment(), CategoriesAdapter.OnCategoryClickInterface {
    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var categoriesViewModel: CategoriesViewModel

    private val categoryAdapter by lazy { CategoriesAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val categoriesViewModelFactory = CategoriesViewModel.CategoriesViewModelFactory()

        categoriesViewModel = ViewModelProvider(this,categoriesViewModelFactory).get()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareCategoriesRv()
        categoriesViewModel.getCategories()
        observeCategoryList()
    }

    private fun prepareCategoriesRv(){
        binding.rvCategory.apply {
            layoutManager = GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
            adapter = categoryAdapter
        }
    }

    private fun observeCategoryList(){
        categoriesViewModel.observeCategoryList().observe(viewLifecycleOwner) {
            categoryAdapter.setCategoryList(it as ArrayList<Category>)
            it.forEach { category ->
                Log.d("Test", category.strCategory)
            }
        }
    }

    override fun onCategoryClick(category: Category) {
        val intent = Intent(activity, CategoryMealsActivity::class.java)
        intent.putExtra(HomeFragment.CATEGORY_NAME, category.strCategory)
        startActivity(intent)
    }
}