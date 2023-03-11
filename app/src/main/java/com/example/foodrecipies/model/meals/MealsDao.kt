package com.example.foodrecipies.model.meals

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MealsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: Meal)

    @Delete
    suspend fun delete(meal: Meal)

    @Query("SELECT * FROM mealInstructions")
    fun getAllMeals(): LiveData<List<Meal>>
}