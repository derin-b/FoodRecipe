package com.example.foodrecipies.nerwork

import com.example.foodrecipies.model.category.CategoryList
import com.example.foodrecipies.model.meals.MealList
import com.example.foodrecipies.model.popularMeals.PopularMealsList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("categories.php")
    fun getMealCategories(): Call<CategoryList>

    @GET("filter.php?")
    fun getCategoryByName(@Query("c") category:String): Call<PopularMealsList>

    @GET("filter.php?")
    fun getPopularItems(@Query("c") category:String): Call<PopularMealsList>

    @GET("lookup.php?")
    fun getMealDetailById(@Query("i") id:String): Call<MealList>

    @GET("search.php")
    fun searchMeals(@Query("s") searchQuery:String): Call<MealList>

}