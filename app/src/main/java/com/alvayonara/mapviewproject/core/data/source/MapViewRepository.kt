package com.alvayonara.mapviewproject.core.data.source

import com.alvayonara.mapviewproject.core.data.source.remote.RemoteDataSource
import com.alvayonara.mapviewproject.core.data.source.remote.network.Result
import com.alvayonara.mapviewproject.core.data.source.remote.response.AutocompleteResponse
import com.alvayonara.mapviewproject.core.data.source.remote.response.DetailsResponse
import com.alvayonara.mapviewproject.core.data.source.remote.response.GeocodeResponse
import com.alvayonara.mapviewproject.domain.repository.IMapViewRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapViewRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource) :
    IMapViewRepository {

    override suspend fun getGeocode(
        latitude: String,
        longitude: String
    ): Flow<Result<GeocodeResponse>> = remoteDataSource.getGeocode(latitude, longitude)

    override suspend fun getAutocomplete(
        address: String,
        deviceId: String
    ): Flow<Result<AutocompleteResponse>> =
        remoteDataSource.getAutocomplete(address, deviceId)

    override suspend fun getDetails(placeId: String): Flow<Result<DetailsResponse>> =
        remoteDataSource.getDetails(placeId)
}