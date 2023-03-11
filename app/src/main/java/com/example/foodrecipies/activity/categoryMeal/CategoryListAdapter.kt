package com.example.foodrecipies.activity.categoryMeal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodrecipies.databinding.MealItemBinding
import com.example.foodrecipies.model.popularMeals.PopularMeals

class CategoryListAdapter(private val onCategoryClickInterface: OnCategoryInterface)
    :RecyclerView.Adapter<CategoryListAdapter.CategoryListViewHolder>() {
    private var categoryList = ArrayList<PopularMeals>()

    fun setCategoryList(categoryList: ArrayList<PopularMeals>) {
        this.categoryList = categoryList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListViewHolder {
        return CategoryListViewHolder(
            MealItemBinding.inflate(LayoutInflater
            .from(parent.context), parent,false))
    }

    override fun onBindViewHolder(holder: CategoryListViewHolder, position: Int) {
        val category = categoryList[position]
        holder.name.text = category.strMeal
        Glide.with(holder.itemView)
            .load(category.strMealThumb)
            .into(holder.image)

        holder.itemView.setOnClickListener {
            onCategoryClickInterface.onCategoryClick(category)
        }

    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    inner class CategoryListViewHolder(binding: MealItemBinding) : RecyclerView.ViewHolder(binding.root){
        val image = binding.ivMeal
        val name = binding.tvMeal
    }

    interface OnCategoryInterface{
        fun onCategoryClick(popularMeals: PopularMeals)
    }

}