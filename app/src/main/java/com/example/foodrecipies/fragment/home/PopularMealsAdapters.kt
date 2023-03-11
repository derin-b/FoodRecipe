package com.example.foodrecipies.fragment.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodrecipies.databinding.PopularItemsBinding
import com.example.foodrecipies.model.popularMeals.PopularMeals

class PopularMealsAdapters(private val onItemClick: OnItemClickInterface,
                           private val onLongItemClick:OnLongItemClickInterface
):RecyclerView.Adapter<PopularMealsAdapters.PopularMealsViewHolder>() {
    private var mealsList = ArrayList<PopularMeals>()

    fun setMealList(mealsList: ArrayList<PopularMeals>) {
        this.mealsList = mealsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealsViewHolder {
        return PopularMealsViewHolder(
            PopularItemsBinding.inflate(LayoutInflater
            .from(parent.context), parent,false))
    }

    override fun onBindViewHolder(holder: PopularMealsViewHolder, position: Int) {
        val meal = mealsList[position]
        Glide.with(holder.itemView)
            .load(meal.strMealThumb)
            .into(holder.image)

        holder.itemView.setOnClickListener {
            onItemClick.onItemClick(meal)
        }

        holder.itemView.setOnLongClickListener {
            onLongItemClick.onItemLongClick(meal)
            true
        }
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    inner class PopularMealsViewHolder(binding: PopularItemsBinding) : RecyclerView.ViewHolder(binding.root){
        val image = binding.ivPopularMeal
    }

    interface OnItemClickInterface{
        fun onItemClick(meal:PopularMeals)
    }

    interface OnLongItemClickInterface{
        fun onItemLongClick(meal:PopularMeals)
    }
}