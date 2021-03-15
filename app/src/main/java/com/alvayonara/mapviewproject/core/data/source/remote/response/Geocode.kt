package com.alvayonara.mapviewproject.core.data.source.remote.response

import com.squareup.moshi.Json

data class Geocode(
	@field:Json(name = "plus_code") val plus_code: PlusCode? = null,
	@field:Json(name = "results") val results: List<ResultsItem?>? = null,
	@field:Json(name = "status") val status: String? = null
)

data class ResultsItem(
	@field:Json(name = "formatted_address") val formatted_address: String? = null,
	@field:Json(name = "types") val types: List<String?>? = null,
	@field:Json(name = "geometry") val geometry: Geometry? = null,
	@field:Json(name = "address_components") val address_components: List<AddressComponentsItem?>? = null,
	@field:Json(name = "plus_code") val plus_code: PlusCode? = null,
	@field:Json(name = "place_id") val place_id: String? = null
)

data class Bounds(
	@field:Json(name = "southwest") val southwest: Southwest? = null,
	@field:Json(name = "northeast") val northeast: Northeast? = null
)

data class PlusCode(
	@field:Json(name = "compound_code") val compound_code: String? = null,
	@field:Json(name = "global_code") val global_code: String? = null
)

data class Viewport(
	@field:Json(name = "southwest") val southwest: Southwest? = null,
	@field:Json(name = "northeast") val northeast: Northeast? = null
)

data class Location(
	@field:Json(name = "lng") val lng: Double? = null,
	@field:Json(name = "lat") val lat: Double? = null
)

data class AddressComponentsItem(
	@field:Json(name = "types") val types: List<String?>? = null,
	@field:Json(name = "short_name") val short_name: String? = null,
	@field:Json(name = "long_name") val long_name: String? = null
)

data class Geometry(
	@field:Json(name = "viewport") val viewport: Viewport? = null,
	@field:Json(name = "bounds") val bounds: Bounds? = null,
	@field:Json(name = "location") val location: Location? = null,
	@field:Json(name = "location_type") val location_type: String? = null
)

data class Southwest(
	@field:Json(name = "lng") val lng: Double? = null,
	@field:Json(name = "lat") val lat: Double? = null
)

data class Northeast(
	@field:Json(name = "lng") val lng: Double? = null,
	@field:Json(name = "lat") val lat: Double? = null
)
