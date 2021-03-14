package com.alvayonara.mapviewproject.core.di

import com.alvayonara.mapviewproject.core.data.source.MapViewRepository
import com.alvayonara.mapviewproject.domain.repository.IMapViewRepository
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class])
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(mapViewRepository: MapViewRepository): IMapViewRepository
}