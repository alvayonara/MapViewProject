package com.alvayonara.mapviewproject.core.data.source.remote.response

import com.squareup.moshi.Json

data class Details(
	@field:Json(name = "result") val result: ResultDetails? = null,
	@field:Json(name = "status") val status: String? = null
)

data class ResultDetails(
	@field:Json(name = "formatted_address") val formatted_address: String? = null,
	@field:Json(name = "name") val name: String? = null,
	@field:Json(name = "geometry") val geometry: Geometry? = null,
	@field:Json(name = "address_components") val address_components: List<AddressComponentsItem?>? = null
)
