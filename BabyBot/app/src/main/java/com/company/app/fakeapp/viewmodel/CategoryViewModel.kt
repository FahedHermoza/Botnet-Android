package com.company.app.fakeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.app.fakeapp.model.Category
import com.company.app.fakeapp.model.Data
import com.company.app.fakeapp.model.ImeiEntity
import com.company.app.fakeapp.storage.ImeiDbRepository
import com.company.app.fakeapp.utils.SingleLiveEvent
import kotlinx.coroutines.launch
import java.util.*

class CategoryViewModel(private val repository: ImeiDbRepository) : ViewModel() {
    private val _categories = MutableLiveData<List<Category>>().apply { value = emptyList() }
    val categories: LiveData<List<Category>> = _categories

    val onImei = SingleLiveEvent<String?>()

    fun loadCategory(){
        val data = Data.getCategory()
        _categories.value = data
    }

    fun loadImei() {
        viewModelScope.launch {
            //Insert imei o get first element of table_imei
            val listImei = repository.getAllNotes()
            if (listImei.isEmpty()) {
                var idUnique = UUID.randomUUID().toString()
                repository.insertImei(imeiEntity = ImeiEntity(
                    idUnique
                )
                )
                onImei.value = idUnique
            }else{
                onImei.value = listImei[0].idUnique
            }
        }
    }

}