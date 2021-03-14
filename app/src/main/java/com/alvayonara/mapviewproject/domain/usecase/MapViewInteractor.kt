package com.alvayonara.mapviewproject.domain.usecase

import com.alvayonara.mapviewproject.core.data.source.remote.network.Result
import com.alvayonara.mapviewproject.core.data.source.remote.response.AutocompleteResponse
import com.alvayonara.mapviewproject.core.data.source.remote.response.DetailsResponse
import com.alvayonara.mapviewproject.core.data.source.remote.response.GeocodeResponse
import com.alvayonara.mapviewproject.domain.repository.IMapViewRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MapViewInteractor @Inject constructor(private val mapViewRepository: IMapViewRepository) : MapViewUseCase {

    override suspend fun getGeocode(
        latitude: String,
        longitude: String
    ): Flow<Result<GeocodeResponse>> = mapViewRepository.getGeocode(latitude, longitude)

    override suspend fun getAutocomplete(
        address: String,
        deviceId: String
    ): Flow<Result<AutocompleteResponse>> = mapViewRepository.getAutocomplete(address, deviceId)

    override suspend fun getDetails(placeId: String): Flow<Result<DetailsResponse>> = mapViewRepository.getDetails(placeId)
}