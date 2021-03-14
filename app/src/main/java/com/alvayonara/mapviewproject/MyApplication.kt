package com.alvayonara.mapviewproject

import android.app.Application
import com.alvayonara.mapviewproject.core.di.CoreComponent
import com.alvayonara.mapviewproject.core.di.DaggerCoreComponent
import com.alvayonara.mapviewproject.di.AppComponent
import com.alvayonara.mapviewproject.di.DaggerAppComponent

open class MyApplication : Application() {

    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.factory().create(applicationContext)
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(coreComponent)
    }
}