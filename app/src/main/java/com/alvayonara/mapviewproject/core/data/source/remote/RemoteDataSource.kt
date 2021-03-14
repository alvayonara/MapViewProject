package com.alvayonara.mapviewproject.core.data.source.remote

import com.alvayonara.mapviewproject.core.base.BaseDataSource
import com.alvayonara.mapviewproject.core.data.source.remote.network.ApiService
import com.alvayonara.mapviewproject.core.data.source.remote.network.Result
import com.alvayonara.mapviewproject.core.data.source.remote.response.AutocompleteResponse
import com.alvayonara.mapviewproject.core.data.source.remote.response.DetailsResponse
import com.alvayonara.mapviewproject.core.data.source.remote.response.GeocodeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService): BaseDataSource() {

    suspend fun getGeocode(latitude: String, longitude: String): Flow<Result<GeocodeResponse>> = flow {
        emit(Result.loading(null))
        emit(safeApiCall { apiService.getGeocode(latlng = "${latitude},${longitude}") })
    }.flowOn(Dispatchers.IO)

    suspend fun getAutocomplete(address: String, deviceId: String): Flow<Result<AutocompleteResponse>> = flow {
        emit(Result.loading(null))
        emit(safeApiCall { apiService.getAutocomplete(input = address, sessionToken = deviceId) })
    }.flowOn(Dispatchers.IO)

    suspend fun getDetails(placeId: String): Flow<Result<DetailsResponse>> = flow {
        emit(Result.loading(null))
        emit(safeApiCall { apiService.getDetails(placeId = placeId) })
    }.flowOn(Dispatchers.IO)
}