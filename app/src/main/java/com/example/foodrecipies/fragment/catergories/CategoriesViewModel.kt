package com.example.foodrecipies.fragment.catergories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodrecipies.model.category.Category
import com.example.foodrecipies.model.category.CategoryList
import com.example.foodrecipies.nerwork.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesViewModel:ViewModel() {
    private var categoryListLiveData = MutableLiveData<List<Category>>()


    fun getCategories(){
        RetrofitInstance.mealApi.getMealCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if (response.body() != null){
                    val categoryList = response.body()!!.categories
                    categoryListLiveData.value = categoryList
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("Home Fragment",t.message.toString())
            }


        })
    }

    fun observeCategoryList():LiveData<List<Category>>{
        return categoryListLiveData
    }

    class CategoriesViewModelFactory(
    ): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CategoriesViewModel() as T
        }
    }
}