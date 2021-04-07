package com.company.app.fakeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.company.app.fakeapp.model.Data
import com.company.app.fakeapp.model.Category
import com.company.app.fakeapp.model.Phrase
import java.util.*

class ListViewModel : ViewModel() {

    private val _category = MutableLiveData<Category>()
    val category: LiveData<Category> = _category

    private val _phrases = MutableLiveData<List<Phrase>>().apply { value = emptyList() }
    val phrases: LiveData<List<Phrase>> = _phrases

    fun loadCategory(name: String){
        var item: Category? = null
        Data.getCategory().forEach {
            if(it.name.toLowerCase(Locale.ROOT) == name.toLowerCase(Locale.ROOT)) {
                item = it
            }
        }

        item?.let {
            _category.value = it
        }
    }

    fun loadPrhases(name:String){
        val data = Data.getPhrases().filter { it.category.toLowerCase(Locale.ROOT) == name.toLowerCase(Locale.ROOT) }
        _phrases.value = data
    }
}