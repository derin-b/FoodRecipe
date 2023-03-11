package com.example.foodrecipies.activity.meal

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.foodrecipies.model.meals.Meal
import com.example.foodrecipies.model.meals.MealList
import com.example.foodrecipies.model.meals.MealsRepository
import com.example.foodrecipies.nerwork.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(private val application: Application):ViewModel(){
    private val mealRepository = MealsRepository(application.applicationContext)

    private var mealDetailLiveData = MutableLiveData<Meal>()

    fun getMealDetailById(id:String){
        RetrofitInstance.mealApi.getMealDetailById(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null){
                    val mealDetail = response.body()!!.meals[0]
                    mealDetailLiveData.value = mealDetail
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("Meal Activity",t.message.toString())
            }

        })
    }

    fun observeMealDetail():LiveData<Meal>{
        return mealDetailLiveData
    }

    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            mealRepository.insert(meal)
        }
    }

    class MealViewModelFactory(
        private val application: Application
    ):ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MealViewModel(application) as T
        }
    }
}