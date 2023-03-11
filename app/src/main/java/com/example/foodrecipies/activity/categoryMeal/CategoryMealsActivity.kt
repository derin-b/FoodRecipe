package com.example.foodrecipies.activity.categoryMeal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodrecipies.activity.meal.MealActivity
import com.example.foodrecipies.databinding.ActivityCategoryMealsBinding
import com.example.foodrecipies.fragment.home.HomeFragment
import com.example.foodrecipies.fragment.home.HomeFragment.Companion.CATEGORY_NAME
import com.example.foodrecipies.model.popularMeals.PopularMeals

class CategoryMealsActivity : AppCompatActivity(), CategoryListAdapter.OnCategoryInterface {
    private lateinit var binding: ActivityCategoryMealsBinding
    private lateinit var categoryViewModel: CategoryViewModel
    private val categoryListAdapter by lazy { CategoryListAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()

        categoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]

        categoryViewModel.getCategoryByName(intent.getStringExtra(CATEGORY_NAME)!!)
        categoryViewModel.observeCategoryList().observe(this, Observer {
            binding.tvCategoryCount.text = it.size.toString()
            categoryListAdapter.setCategoryList(it as ArrayList<PopularMeals> )
        })
    }

    private fun prepareRecyclerView() {
        binding.rvMeals.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoryListAdapter
        }
    }

    override fun onCategoryClick(popularMeals: PopularMeals) {
        val intent = Intent(this, MealActivity::class.java)
        intent.putExtra(HomeFragment.MEAL_ID, popularMeals.idMeal)
        intent.putExtra(HomeFragment.MEAL_NAME, popularMeals.strMeal)
        intent.putExtra(HomeFragment.MEAL_THUMB, popularMeals.strMealThumb)
        startActivity(intent)
    }
}