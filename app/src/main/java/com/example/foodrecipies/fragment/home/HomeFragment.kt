package com.example.foodrecipies.fragment.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodrecipies.activity.meal.MealActivity
import com.example.foodrecipies.R
import com.example.foodrecipies.databinding.FragmentHomeBinding
import com.example.foodrecipies.fragment.bottomSheetFragment.MealBottomSheetFragment
import com.example.foodrecipies.model.meals.Meal
import com.example.foodrecipies.model.popularMeals.PopularMeals

class HomeFragment : Fragment(),
    PopularMealsAdapters.OnItemClickInterface,
    PopularMealsAdapters.OnLongItemClickInterface{

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var randomMeal: Meal

    private val popularAdapter by lazy { PopularMealsAdapters(this, this)}

    companion object{
        const val MEAL_ID = "meal_id"
        const val MEAL_NAME = "meal_name"
        const val MEAL_THUMB = "meal_thumb"
        const val CATEGORY_NAME = "category_name"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val application = requireNotNull(this.activity).application
        val homeViewModelFactory = HomeViewModel.HomeViewModelFactory(application)

        homeViewModel = ViewModelProvider(this,homeViewModelFactory).get()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setBinding()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemsRv()

        homeViewModel.getRandomMeal()
        observeRandomMeal()
        onRandomMealClicked()

        homeViewModel.getPopularItems(binding.tvCategory.text.toString())
        observePopularItems()

        onSearchIconClicked()

    }

    override fun onResume() {
        super.onResume()
        binding.tvCategory.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                resources.getStringArray(R.array.category)
            )
        )
    }

    private fun setBinding(){
        binding.tvCategory.setText(getString(R.string.Seafood))

        binding.tvCategory.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                resources.getStringArray(R.array.category)
            )
        )

        binding.tvCategory.doAfterTextChanged {
            homeViewModel.getPopularItems(binding.tvCategory.text.toString())
            observePopularItems()

        }
    }

    private fun onRandomMealClicked() {
        binding.imgRandomMeal.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeRandomMeal(){
        homeViewModel.observeRandomMeal().observe(viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.imgRandomMeal)
            this.randomMeal = meal
        }

    }

    private fun preparePopularItemsRv(){
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = popularAdapter
        }
    }


    private fun observePopularItems() {
        homeViewModel.observePopularItems().observe(viewLifecycleOwner){
            popularAdapter.setMealList(it as ArrayList<PopularMeals>)
        }
    }

    private fun onSearchIconClicked(){
        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment2_to_searchFragment)
        }
    }


    override fun onItemClick(meal: PopularMeals) {
        val intent = Intent(activity, MealActivity::class.java)
        intent.putExtra(MEAL_ID, meal.idMeal)
        intent.putExtra(MEAL_NAME, meal.strMeal)
        intent.putExtra(MEAL_THUMB, meal.strMealThumb)
        startActivity(intent)
    }

    override fun onItemLongClick(meal: PopularMeals) {
        val mealBottomSheetFragment = MealBottomSheetFragment.newInstance(meal.idMeal)
        mealBottomSheetFragment.show(childFragmentManager, "Meal Info")
    }

}