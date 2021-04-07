package com.company.app.fakeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.company.app.fakeapp.storage.ImeiDbRepository

class CategoryViewModelFactory(private val repository: ImeiDbRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CategoryViewModel(repository) as T
    }
}