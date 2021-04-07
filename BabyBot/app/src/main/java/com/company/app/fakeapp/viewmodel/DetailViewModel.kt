package com.company.app.fakeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.company.app.fakeapp.model.Data
import com.company.app.fakeapp.model.Phrase

class DetailViewModel : ViewModel() {

    private val _phrase = MutableLiveData<Phrase>()
    val phrase: LiveData<Phrase> = _phrase

    fun loadPhrase( id: Int){
        Data.getPhrases().forEach {
            if(it.id == id)
                _phrase.value = it
        }
    }
}