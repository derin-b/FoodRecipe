package com.example.foodrecipies.fragment.catergories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodrecipies.databinding.CategoryCardBinding
import com.example.foodrecipies.model.category.Category


class CategoriesAdapter(
    private val onCategoryClickInterface: OnCategoryClickInterface
):RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {
    private var categoryList = ArrayList<Category>()

    fun setCategoryList(categoryList: ArrayList<Category>) {
        this.categoryList = categoryList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            CategoryCardBinding.inflate(LayoutInflater
            .from(parent.context), parent,false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]
        holder.name.text = category.strCategory
        Glide.with(holder.itemView)
            .load(category.strCategoryThumb)
            .into(holder.image)

        holder.itemView.setOnClickListener {
            onCategoryClickInterface.onCategoryClick(category)
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    inner class CategoryViewHolder(binding: CategoryCardBinding) : RecyclerView.ViewHolder(binding.root){
        val image = binding.imgCategory
        val name = binding.tvCategoryName
    }

    interface OnCategoryClickInterface{
        fun onCategoryClick(category:Category)
    }

}