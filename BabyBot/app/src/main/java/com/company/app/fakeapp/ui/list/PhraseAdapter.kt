package com.company.app.fakeapp.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.company.app.fakeapp.databinding.ItemPhraseBinding
import com.company.app.fakeapp.model.Phrase

class PhraseAdapter(private var phrases:List<Phrase>,
                    val itemAction: (item: Phrase) -> Unit)
    :RecyclerView.Adapter<PhraseAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        var itemBinding: ItemPhraseBinding = ItemPhraseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //render
        with(holder){
            with(phrases[position]) {
                binding.root.setOnClickListener {
                    itemAction(this)
                }

                binding.textViewNameItemCategory.text = description
                binding.imageViewItemCategory.setImageResource(photo)
            }
        }
    }

    override fun getItemCount(): Int {
        return phrases.size
    }

    fun update(data:List<Phrase>){
        phrases = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemPhraseBinding)
        :RecyclerView.ViewHolder(binding.root)
}