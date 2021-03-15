package com.alvayonara.mapviewproject.core.data.source.remote

import com.alvayonara.mapviewproject.core.data.source.remote.network.ApiService
import com.alvayonara.mapviewproject.core.data.source.remote.network.Result
import com.alvayonara.mapviewproject.core.data.source.remote.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getGeocode(latitude: String, longitude: String): Flow<Result<List<ResultsItem?>>> =
        flow {
            emit(Result.loading(null))
            try {
                val response = apiService.getGeocode(latlng = "${latitude},${longitude}")
                val dataArray = response.results
                emit(Result.success(dataArray))
            } catch (e: Exception) {
                emit(Result.error(message = e.message.toString(), data = null))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getAutocomplete(
        address: String,
        deviceId: String
    ): Flow<Result<List<PredictionsItem?>>> = flow {
        emit(Result.loading(null))
        try {
            val response = apiService.getAutocomplete(input = address, sessionToken = deviceId)
            val dataArray = response.predictions
            emit(Result.success(dataArray))
        } catch (e: Exception) {
            emit(Result.error(message = e.message.toString(), data = null))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getDetails(placeId: String): Flow<Result<ResultDetails?>> = flow {
        emit(Result.loading(null))
        try {
            val response = apiService.getDetails(placeId = placeId)
            val dataArray = response.result
            emit(Result.success(dataArray))
        } catch (e: Exception) {
            emit(Result.error(message = e.message.toString(), data = null))
        }
    }.flowOn(Dispatchers.IO)
}