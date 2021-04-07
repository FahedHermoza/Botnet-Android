package com.company.app.fakeapp.ui.permission

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.company.app.fakeapp.R

class PermissionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.permissions_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PermissionsFragment.newInstance())
                .commitNow()
        }
    }
}