package com.alvayonara.mapviewproject.core.data.source.remote.network

import com.alvayonara.mapviewproject.BuildConfig
import com.alvayonara.mapviewproject.core.data.source.remote.response.AutocompleteResponse
import com.alvayonara.mapviewproject.core.data.source.remote.response.DetailsResponse
import com.alvayonara.mapviewproject.core.data.source.remote.response.GeocodeResponse
import com.alvayonara.mapviewproject.core.utils.ApiUrl.AUTOCOMPLETE
import com.alvayonara.mapviewproject.core.utils.ApiUrl.DETAILS
import com.alvayonara.mapviewproject.core.utils.ApiUrl.GEOCODE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(GEOCODE)
    suspend fun getGeocode(
            @Path("latlng") latlng: String,
            @Query("language") language: String = "id",
            @Query("region") region: String = "id",
            @Query("key") key: String = BuildConfig.API_KEY
    ): Response<GeocodeResponse>

    @GET(AUTOCOMPLETE)
    suspend fun getAutocomplete(
            @Query("input") input: String,
            @Query("sessiontoken") sessionToken: String,
            @Query("radius") radius: String = "1",
            @Query("language") language: String = "id",
            @Query("components") components: String = "country:id",
            @Query("key") key: String = BuildConfig.API_KEY
    ): Response<AutocompleteResponse>

    @GET(DETAILS)
    suspend fun getDetails(
            @Query("place_id") placeId: String,
            @Query("language") language: String = "id",
            @Query("region") region: String = "id",
            @Query("fields") fields: String = "address_component,name,geometry,formatted_address",
            @Query("key") key: String = BuildConfig.API_KEY
    ): Response<DetailsResponse>
}