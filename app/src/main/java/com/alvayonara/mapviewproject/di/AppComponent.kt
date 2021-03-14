package com.alvayonara.mapviewproject.di

import com.alvayonara.mapviewproject.core.di.CoreComponent
import com.alvayonara.mapviewproject.ui.map.MapsActivity
import com.alvayonara.mapviewproject.ui.search.SearchActivity
import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AppScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [AppModule::class, ViewModelModule::class]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): AppComponent
    }

    fun inject(activity: MapsActivity)
    fun inject(activity: SearchActivity)
}