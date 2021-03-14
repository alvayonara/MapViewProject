package com.alvayonara.mapviewproject.core.data.source.remote.response

import com.squareup.moshi.Json

data class DetailsResponse(
	@field:Json(name="result") val result: Result? = null,
	@field:Json(name="status") val status: String? = null
)

data class Result(
	@field:Json(name="formatted_address") val formatted_address: String? = null,
	@field:Json(name="name") val name: String? = null,
	@field:Json(name="geometry") val geometry: Geometry? = null,
	@field:Json(name="address_components") val address_components: List<AddressComponentsItem?>? = null
)
