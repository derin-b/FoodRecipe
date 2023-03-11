package com.example.foodrecipies.fragment.search

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.foodrecipies.model.category.Category
import com.example.foodrecipies.model.category.CategoryList
import com.example.foodrecipies.model.meals.MealList
import com.example.foodrecipies.model.meals.Meal
import com.example.foodrecipies.model.meals.MealsRepository
import com.example.foodrecipies.model.popularMeals.PopularMeals
import com.example.foodrecipies.model.popularMeals.PopularMealsList
import com.example.foodrecipies.nerwork.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(private val application: Application):ViewModel() {
    private val mealsRepository = MealsRepository(application.applicationContext)

   private var searchMealLiveData = MutableLiveData<List<Meal>>()

    fun searchMeal(searchQuery:String){
        RetrofitInstance.mealApi.searchMeals(searchQuery).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val mealList = response.body()?.meals
                mealList?.let {
                    searchMealLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("Meal Activity",t.message.toString())
            }

        })
    }
    fun observeSearchMeal():LiveData<List<Meal>>{
        return searchMealLiveData
    }


    class SearchViewModelFactory(
        private val application: Application
    ): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchViewModel(application) as T
        }
    }
}