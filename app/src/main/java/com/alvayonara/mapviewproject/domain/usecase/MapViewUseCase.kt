package com.alvayonara.mapviewproject.domain.usecase

import com.alvayonara.mapviewproject.core.data.source.remote.network.Result
import com.alvayonara.mapviewproject.core.data.source.remote.response.*
import kotlinx.coroutines.flow.Flow

interface MapViewUseCase {

    suspend fun getGeocode(latitude: String, longitude: String): Flow<Result<List<ResultsItem?>>>

    suspend fun getAutocomplete(address: String, deviceId: String): Flow<Result<List<PredictionsItem?>>>

    suspend fun getDetails(placeId: String): Flow<Result<ResultDetails?>>
}