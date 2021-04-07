package com.company.app.fakeapp.ui.category

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.company.app.fakeapp.R
import com.company.app.fakeapp.model.Category
import com.company.app.fakeapp.utils.inflate
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter(private var categories:List<Category>,
                      val itemAction: (item: Category) -> Unit)
    :RecyclerView.Adapter<CategoryAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            parent.inflate(R.layout.item_category)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //render
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    fun update(data:List<Category>){
        categories = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view){

        private val textViewName:TextView = view.textViewNameItemCategory
        private val imageView:ImageView = view.imageViewItemCategory

        fun bind(category: Category){
            textViewName.text = category.name
            imageView.setImageResource(category.logo)

            view.setOnClickListener {
                itemAction(category)
            }
        }
    }
}