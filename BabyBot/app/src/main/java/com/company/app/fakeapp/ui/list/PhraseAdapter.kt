package com.company.app.fakeapp.ui.list

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.company.app.fakeapp.R
import com.company.app.fakeapp.model.Phrase
import com.company.app.fakeapp.utils.inflate
import kotlinx.android.synthetic.main.item_category.view.*

class PhraseAdapter(private var phrases:List<Phrase>,
                    val itemAction: (item: Phrase) -> Unit)
    :RecyclerView.Adapter<PhraseAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            parent.inflate(R.layout.item_phrase)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //render
        holder.bind(phrases[position])
    }

    override fun getItemCount(): Int {
        return phrases.size
    }

    fun update(data:List<Phrase>){
        phrases = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view){

        private val textViewName:TextView = view.textViewNameItemCategory
        private val imageView:ImageView = view.imageViewItemCategory

        fun bind(phrase: Phrase){
            textViewName.text = phrase.description
            imageView.setImageResource(phrase.photo)

            view.setOnClickListener {
                itemAction(phrase)
            }
        }
    }
}