package com.example.foodrecipies.activity.meal

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.bumptech.glide.Glide
import com.example.foodrecipies.R
import com.example.foodrecipies.databinding.ActivityMealBinding
import com.example.foodrecipies.fragment.home.HomeFragment
import com.example.foodrecipies.model.meals.Meal

class MealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealViewModel: MealViewModel

    private var mealType: Meal? = null
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var youTubeLink: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelFactory = MealViewModel.MealViewModelFactory(application)

        mealViewModel = ViewModelProvider(this, viewModelFactory).get()

        getMealInfoFromIntent()
        loadingCase()
        mealViewModel.getMealDetailById(mealId)
        observeMealDetail()
        setBinding()

        onFavButtonClicked()
    }

    private fun getMealInfoFromIntent() {
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun onFavButtonClicked(){
        binding.fab.setOnClickListener {
            mealType?.let {
                mealViewModel.insertMeal(it)
                Toast.makeText(this, "Meal Saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setBinding(){
        Glide.with(this)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingBar.title = mealName
        binding.collapsingBar.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.white))
        binding.collapsingBar.setExpandedTitleColor(ContextCompat.getColor(this, R.color.white))

        //move to youtube onclick
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youTubeLink))
            startActivity(intent)
        }
    }

    private fun observeMealDetail(){
        mealViewModel.observeMealDetail().observe(this, object : Observer<Meal> {
            override fun onChanged(meal: Meal?) {
                onResponseCase()
                mealType = meal
                binding.tvCategoryInfo.text = getString(R.string.category_beef, meal!!.strCategory)
                binding.tvAreaInfo.text = getString(R.string.area, meal!!.strArea)
                binding.tvContent.text = meal.strInstructions
                youTubeLink = meal.strYoutube.toString()
            }

        })
    }

    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.fab.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvAreaInfo.visibility = View.INVISIBLE
        binding.tvCategoryInfo.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }

    private fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.fab.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvAreaInfo.visibility = View.VISIBLE
        binding.tvCategoryInfo.visibility = View.VISIBLE
        binding.tvContent.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }
}