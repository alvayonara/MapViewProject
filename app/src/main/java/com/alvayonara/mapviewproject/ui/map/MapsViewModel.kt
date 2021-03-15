package com.alvayonara.mapviewproject.ui.map

import androidx.lifecycle.*
import com.alvayonara.mapviewproject.domain.usecase.MapViewUseCase
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class MapsViewModel @Inject constructor(private val mapViewUseCase: MapViewUseCase) : ViewModel() {

    private val latLng = MutableLiveData<LatLng>()
    private val placeId = MutableLiveData<String>()

    fun setSelectedLatLng(latLng: LatLng) {
        this.latLng.value = latLng
    }

    fun setSelectedPlaceId(placeId: String) {
        this.placeId.value = placeId
    }

    val getGeocode = latLng.switchMap {
        liveData {
            emitSource(
                mapViewUseCase.getGeocode(it.latitude.toString(), it.longitude.toString())
                    .asLiveData()
            )
        }
    }

    val getDetail = placeId.switchMap {
        liveData {
            emitSource(
                mapViewUseCase.getDetails(it)
                    .asLiveData()
            )
        }
    }
}