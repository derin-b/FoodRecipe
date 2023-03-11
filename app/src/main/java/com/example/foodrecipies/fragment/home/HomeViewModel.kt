package com.example.foodrecipies.fragment.home

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

class HomeViewModel(private val application: Application):ViewModel() {
    private val mealsRepository = MealsRepository(application.applicationContext)

    var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<PopularMeals>>()
   // private val favouriteMealsLiveData = mealRepository.getAllMeals()
   private var mealDetailLiveData = MutableLiveData<Meal>()
   // private var searchMealLiveData = MutableLiveData<List<Meal>>()

    fun getRandomMeal(){
        RetrofitInstance.mealApi.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null){
                    val randomMeal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("Home Fragment","Meal Error")
            }

        })
    }

    fun observeRandomMeal():LiveData<Meal>{
        return randomMealLiveData
    }

    fun getPopularItems(category:String){
        RetrofitInstance.mealApi.getPopularItems(category).enqueue(object : Callback<PopularMealsList> {
            override fun onResponse(call: Call<PopularMealsList>, response: Response<PopularMealsList>) {
                if (response.body() != null){
                    val popularItem = response.body()!!.meals
                    popularItemsLiveData.value = popularItem
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<PopularMealsList>, t: Throwable) {
                Log.d("Home Fragment",t.message.toString())
            }


        })
    }

    fun observePopularItems():LiveData<List<PopularMeals>>{
        return popularItemsLiveData
    }

   /* fun observeFavouriteMeals():LiveData<List<Meal>>{
        return favouriteMealsLiveData
    }

    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            mealRepository.delete(meal)
        }
    }
    */

    fun getMealDetailById(id:String){
        RetrofitInstance.mealApi.getMealDetailById(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val mealDetail = response.body()?.meals?.first()
                mealDetail?.let {
                    mealDetailLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("Meal Activity",t.message.toString())
            }

        })
    }

    /*fun searchMeal(searchQuery:String){
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
    */

    fun observeBottomSheetMeal():LiveData<Meal>{
        return mealDetailLiveData
    }

    /*
    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            mealRepository.insert(meal)
        }
    }

     */

    class HomeViewModelFactory(
        private val application: Application
    ): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(application) as T
        }
    }
}