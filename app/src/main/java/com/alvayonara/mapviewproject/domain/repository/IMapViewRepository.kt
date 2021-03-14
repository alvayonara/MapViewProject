package com.alvayonara.mapviewproject.domain.repository

import com.alvayonara.mapviewproject.core.data.source.remote.network.Result
import com.alvayonara.mapviewproject.core.data.source.remote.response.AutocompleteResponse
import com.alvayonara.mapviewproject.core.data.source.remote.response.DetailsResponse
import com.alvayonara.mapviewproject.core.data.source.remote.response.GeocodeResponse
import kotlinx.coroutines.flow.Flow

interface IMapViewRepository {

    suspend fun getGeocode(latitude: String, longitude: String): Flow<Result<GeocodeResponse>>

    suspend fun getAutocomplete(address: String, deviceId: String): Flow<Result<AutocompleteResponse>>

    suspend fun getDetails(placeId: String): Flow<Result<DetailsResponse>>
}