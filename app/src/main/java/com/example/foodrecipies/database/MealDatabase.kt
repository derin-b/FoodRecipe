package com.example.foodrecipies.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.foodrecipies.model.meals.Meal
import com.example.foodrecipies.model.meals.MealsDao

@Database(
    entities = [
        Meal::class],
    version = 1, exportSchema = false)

abstract class MealDatabase: RoomDatabase() {
    abstract fun mealDao(): MealsDao

    companion object{
        @Volatile
        private var INSTANCE: MealDatabase? = null

        //Only one thread can have an instance of the database
        @Synchronized
        fun getDatabase(context: Context): MealDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MealDatabase::class.java,
                    "meal_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}