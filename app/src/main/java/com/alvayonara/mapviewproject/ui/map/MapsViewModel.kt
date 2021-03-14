package com.alvayonara.mapviewproject.ui.map

import androidx.lifecycle.ViewModel
import com.alvayonara.mapviewproject.domain.usecase.MapViewUseCase
import javax.inject.Inject

class MapsViewModel @Inject constructor(private val mapViewUseCase: MapViewUseCase): ViewModel() {
}