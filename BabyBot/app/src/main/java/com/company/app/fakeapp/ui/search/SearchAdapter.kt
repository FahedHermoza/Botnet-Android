package com.company.app.fakeapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.company.app.fakeapp.databinding.ListItemSearchBinding
import java.util.*

class SearchAdapter(private var listName: MutableList<String>,
                    private var listNumber: MutableList<String>,
                    var callback: Callback) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private var listNameCopy:  List<String> = listName.toList()
    private var listNumberCopy:  List<String> = listNumber.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemBinding: ListItemSearchBinding = ListItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
                binding.root.setOnClickListener {
                    callback.listener(listName[position], listNumber[position])
                }

                binding.titleSearch.text = listName[position]
        }
    }

    override fun getItemCount() = listName.size

    fun filter(text: String) {
        listName.clear()
        listNumber.clear()
        if(text.isEmpty()){
            listName.addAll(listNameCopy)
            listNumber.addAll(listNumberCopy)
        } else{
            var text = text.toLowerCase(Locale.ROOT)
            for((index, name) in listNameCopy.withIndex()){
                if(name.toLowerCase(Locale.ROOT).contains(text)){
                    listName.add(name)
                    listNumber.add(listNumberCopy[index])
                }
            }
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ListItemSearchBinding)
        :RecyclerView.ViewHolder(binding.root)

    interface Callback{
        fun listener(name: String, number: String)
    }
}