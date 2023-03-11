package com.example.foodrecipies.fragment.favourites

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipies.databinding.FragmentFavouritesBinding
import com.google.android.material.snackbar.Snackbar

class FavouritesFragment : Fragment() {

    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var favouritesViewModel: FavouritesViewModel

    private val favouriteAdapter by lazy { MealsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val application = requireNotNull(this.activity).application
        val favouritesViewModelFactory = FavouritesViewModel.FavouritesViewModelFactory(application)

        favouritesViewModel = ViewModelProvider(this,favouritesViewModelFactory).get()

        binding = FragmentFavouritesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRv()
        observeFavourites()

        //To swipe to delete
        //@dragDirs: is the direction the layout scrolls
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val meal = favouriteAdapter.differ.currentList[position]
                favouritesViewModel.deleteMeal(meal)
                Snackbar.make(requireView(), "Meal deleted from favourite", Snackbar.LENGTH_LONG).setAction(
                    "Undo",View.OnClickListener {
                        favouritesViewModel.insertMeal(meal)
                    }
                ).show()

            }

        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavourite)
    }

    private fun prepareRv() {
        binding.rvFavourite.apply {
            layoutManager = GridLayoutManager(context,
                2, GridLayoutManager.VERTICAL, false)
            adapter = favouriteAdapter
        }
    }

    private fun observeFavourites(){
        favouritesViewModel.observeFavouriteMeals().observe(viewLifecycleOwner, Observer {
            it?.forEach { meal ->
                Log.d("Favourites", meal.strMeal.toString())
            }
            favouriteAdapter.differ.submitList(it)
        })
    }
}