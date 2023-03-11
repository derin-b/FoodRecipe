package com.example.foodrecipies.model.meals

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.foodrecipies.database.MealDatabase

class MealsRepository(context: Context) {
    private val mealDatabase = MealDatabase.getDatabase(context)
    suspend fun insert(meal: Meal){
        mealDatabase.mealDao().insertMeal(meal)
    }

    suspend fun delete(meal: Meal){
        mealDatabase.mealDao().delete(meal)
    }

    fun getAllMeals(): LiveData<List<Meal>> {
        return mealDatabase.mealDao().getAllMeals()
    }
}