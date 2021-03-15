package com.alvayonara.mapviewproject.core.data.source.remote.response

import com.squareup.moshi.Json

data class Autocomplete(
    @field:Json(name = "predictions") val predictions: List<PredictionsItem?>? = null,
    @field:Json(name = "status") val status: String? = null
)

data class StructuredFormatting(
    @field:Json(name = "main_text_matched_substrings") val main_text_matched_substrings: List<MainTextMatchedSubstringsItem?>? = null,
    @field:Json(name = "secondary_text_matched_substrings") val secondary_text_matched_substrings: List<SecondaryTextMatchedSubstringsItem?>? = null,
    @field:Json(name = "secondary_text") val secondary_text: String? = null,
    @field:Json(name = "main_text") val main_text: String? = null
)

data class MatchedSubstringsItem(
    @field:Json(name = "offset") val offset: Int? = null,
    @field:Json(name = "length") val length: Int? = null
)

data class TermsItem(
    @field:Json(name = "offset") val offset: Int? = null,
    @field:Json(name = "value") val value: String? = null
)

data class SecondaryTextMatchedSubstringsItem(
    @field:Json(name = "offset") val offset: Int? = null,
    @field:Json(name = "length") val length: Int? = null
)

data class PredictionsItem(
    @field:Json(name = "reference") val reference: String? = null,
    @field:Json(name = "types") val types: List<String?>? = null,
    @field:Json(name = "matched_substrings") val matched_substrings: List<MatchedSubstringsItem?>? = null,
    @field:Json(name = "terms") val terms: List<TermsItem?>? = null,
    @field:Json(name = "structured_formatting") val structured_formatting: StructuredFormatting? = null,
    @field:Json(name = "description") val description: String? = null,
    @field:Json(name = "place_id") val place_id: String? = null
)

data class MainTextMatchedSubstringsItem(
    @field:Json(name = "offset") val offset: Int? = null,
    @field:Json(name = "length") val length: Int? = null
)
