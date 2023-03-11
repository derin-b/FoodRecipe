package com.example.foodrecipies.fragment.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodrecipies.databinding.CategoryCardBinding
import com.example.foodrecipies.model.meals.Meal

class MealsAdapter(): RecyclerView.Adapter<MealsAdapter.FavouriteMealViewHolder>() {

    inner class FavouriteMealViewHolder(val binding: CategoryCardBinding)
        :RecyclerView.ViewHolder(binding.root){}

    /*diffUtil is used when items in the recycler view changes
     instead of reloading the whole list it will make changes to the list
     */
    private val diffUtil = object : DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteMealViewHolder {
        return FavouriteMealViewHolder(
            CategoryCardBinding
            .inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: FavouriteMealViewHolder, position: Int) {
        val meal = differ.currentList[position]
        Glide.with(holder.itemView)
            .load(meal.strMealThumb)
            .into(holder.binding.imgCategory)

        holder.binding.tvCategoryName.text = meal.strMeal
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}