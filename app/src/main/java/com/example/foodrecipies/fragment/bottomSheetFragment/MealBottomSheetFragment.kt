package com.example.foodrecipies.fragment.bottomSheetFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.bumptech.glide.Glide
import com.example.foodrecipies.activity.meal.MealActivity
import com.example.foodrecipies.databinding.FragmentMealBottomSheetBinding
import com.example.foodrecipies.fragment.home.HomeFragment
import com.example.foodrecipies.fragment.home.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val MEAL_ID = "mealId"

class MealBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMealBottomSheetBinding
    private lateinit var homeViewModel: HomeViewModel
    private var mealId: String? = null
    private var mealName: String? = null
    private var mealThumb: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val application = requireNotNull(this.activity).application
        val homeViewModelFactory = HomeViewModel.HomeViewModelFactory(application)

        homeViewModel = ViewModelProvider(this,homeViewModelFactory).get()
        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentMealBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mealId?.let {
            homeViewModel.getMealDetailById(it)
        }

        observeBottomSheetMeal()
        onBottomSheetClicked()
    }

    private fun onBottomSheetClicked() {
        binding.layout.setOnClickListener {
            if (mealName != null && mealThumb != null) {
                val intent = Intent(activity, MealActivity::class.java)
                intent.apply {
                    putExtra(HomeFragment.MEAL_ID, mealId)
                    putExtra(HomeFragment.MEAL_NAME, mealName)
                    putExtra(HomeFragment.MEAL_THUMB, mealThumb)
                }
                startActivity(intent)
            }
        }
    }

    private fun observeBottomSheetMeal() {
        homeViewModel.observeBottomSheetMeal().observe(viewLifecycleOwner, Observer {
            Glide.with(requireContext())
                .load(it.strMealThumb)
                .into(binding.imgBottomSheet)
            mealName = it.strMeal
            mealThumb = it.strMealThumb

            binding.tvMealName.text = mealName
            binding.tvLocation.text= it.strArea
            binding.tvCategory.text = it.strCategory

        })
    }

    companion object {
        @JvmStatic fun newInstance(param1: String) =
                MealBottomSheetFragment().apply {
                    arguments = Bundle().apply {
                        putString(MEAL_ID, param1)
                    }
                }
    }
}