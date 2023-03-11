package com.example.foodrecipies.activity.categoryMeal

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodrecipies.model.popularMeals.PopularMeals
import com.example.foodrecipies.model.popularMeals.PopularMealsList
import com.example.foodrecipies.nerwork.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel:ViewModel() {
    private var categoryLiveData = MutableLiveData<List<PopularMeals>>()

    fun getCategoryByName(categoryName:String){
        RetrofitInstance.mealApi.getCategoryByName(categoryName).enqueue(object : Callback<PopularMealsList> {
            override fun onResponse(
                call: Call<PopularMealsList>,
                response: Response<PopularMealsList>
            ) {
                response.body()?.let {
                    categoryLiveData.postValue(it.meals)
                }
            }

            override fun onFailure(call: Call<PopularMealsList>, t: Throwable) {
                Log.d("Category Fragment", t.message!!)
            }


        })
    }

    fun observeCategoryList():LiveData<List<PopularMeals>>{
        return categoryLiveData
    }
}