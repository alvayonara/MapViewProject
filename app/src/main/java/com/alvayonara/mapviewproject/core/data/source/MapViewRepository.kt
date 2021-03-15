package com.alvayonara.mapviewproject.core.data.source

import com.alvayonara.mapviewproject.core.data.source.remote.RemoteDataSource
import com.alvayonara.mapviewproject.core.data.source.remote.network.Result
import com.alvayonara.mapviewproject.core.data.source.remote.response.*
import com.alvayonara.mapviewproject.domain.repository.IMapViewRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapViewRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) :
    IMapViewRepository {

    override suspend fun getGeocode(
        latitude: String,
        longitude: String
    ): Flow<Result<List<ResultsItem?>>> = remoteDataSource.getGeocode(latitude, longitude)

    override suspend fun getAutocomplete(
        address: String,
        deviceId: String
    ): Flow<Result<List<PredictionsItem?>>> =
        remoteDataSource.getAutocomplete(address, deviceId)

    override suspend fun getDetails(placeId: String): Flow<Result<ResultDetails?>> =
        remoteDataSource.getDetails(placeId)
}