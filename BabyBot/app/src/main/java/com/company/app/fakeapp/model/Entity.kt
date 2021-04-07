package com.company.app.fakeapp.model

import java.io.Serializable

data class Category(val name: String, val description: String, val logo: Int)

data class Phrase(val id:Int, val category: String, val description: String, val photo: Int): Serializable

data class ContactPhone(val name: String, val number:String, val time:String)

data class ImeiEntity(val idUnique: String)