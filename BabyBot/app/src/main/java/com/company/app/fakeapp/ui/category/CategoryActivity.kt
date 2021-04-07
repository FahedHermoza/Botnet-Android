package com.company.app.fakeapp.ui.category

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.company.app.fakeapp.R

class CategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.category_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CategoryFragment.newInstance())
                .commitNow()
        }
    }
}