package com.company.app.fakeapp

import android.app.Application
import com.squareup.picasso.core.LogUtils
import com.squareup.picasso.di.Injector

class App : Application() {

  override fun onCreate() {
    super.onCreate()
    LogUtils.setup(BuildConfig.DEBUG)
    Injector.setup(this)
  }
}