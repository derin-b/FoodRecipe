package com.example.foodrecipies.model.meals

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mealInstructions")
data class Meal(
    @PrimaryKey
    val idMeal: String,
    val strArea: String?,
    val strCategory: String?,
    val strInstructions: String?,
    val strMeal: String?,
    val strMealThumb: String?,
    val strYoutube: String?
)