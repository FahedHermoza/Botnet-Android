package com.company.app.fakeapp.ui.search

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.company.app.fakeapp.R
import com.company.app.fakeapp.utils.inflate
import kotlinx.android.synthetic.main.list_item_search.view.*
import java.util.*

class SearchAdapter(private var listName: MutableList<String>,
                    private var listNumber: MutableList<String>,
                    var callback: Callback) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private var listNameCopy:  List<String> = listName.toList()
    private var listNumberCopy:  List<String> = listNumber.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.list_item_search))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listName[position], listNumber[position], callback)
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

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(name: String, number: String, callback: Callback) {
            itemView.title_search.text = name

            itemView.setOnClickListener {
                callback.listener(name, number)
            }
        }
    }

    interface Callback{
        fun listener(name: String, number: String)
    }
}