package com.company.app.fakeapp.ui.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.company.app.fakeapp.R
import com.company.app.fakeapp.databinding.ItemCategoryBinding
import com.company.app.fakeapp.model.Category
import com.company.app.fakeapp.utils.inflate

class CategoryAdapter(private var categories:List<Category>,
                      val itemAction: (item: Category) -> Unit)
    :RecyclerView.Adapter<CategoryAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        var itemBinding: ItemCategoryBinding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false);
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //render
        with(holder){
            with(categories[position]) {
                binding.root.setOnClickListener {
                    itemAction(this)
                }

                binding.textViewNameItemCategory.text = name
                binding.imageViewItemCategory.setImageResource(logo)
            }
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    fun update(data:List<Category>){
        categories = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemCategoryBinding)
        :RecyclerView.ViewHolder(binding.root)
}