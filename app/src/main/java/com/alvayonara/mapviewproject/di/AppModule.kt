package com.alvayonara.mapviewproject.di

import com.alvayonara.mapviewproject.domain.usecase.MapViewInteractor
import com.alvayonara.mapviewproject.domain.usecase.MapViewUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    abstract fun provideMovieUseCase(mapViewInteractor: MapViewInteractor): MapViewUseCase
}