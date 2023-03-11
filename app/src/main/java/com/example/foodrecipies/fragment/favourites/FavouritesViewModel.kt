package com.example.foodrecipies.fragment.favourites

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
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavouritesViewModel(private val application: Application):ViewModel() {
    private val mealsRepository = MealsRepository(application.applicationContext)

    var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<PopularMeals>>()
    private val favouriteMealsLiveData = mealsRepository.getAllMeals()
   private var mealDetailLiveData = MutableLiveData<Meal>()
   // private var searchMealLiveData = MutableLiveData<List<Meal>>()

    fun observeFavouriteMeals():LiveData<List<Meal>>{
        return favouriteMealsLiveData
    }

    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            mealsRepository.delete(meal)
        }
    }

    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            mealsRepository.insert(meal)
        }
    }

    class FavouritesViewModelFactory(
        private val application: Application
    ): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FavouritesViewModel(application) as T
        }
    }
}