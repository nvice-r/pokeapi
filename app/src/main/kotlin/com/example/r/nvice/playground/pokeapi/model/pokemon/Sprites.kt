package com.example.r.nvice.playground.pokeapi.model.pokemon

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Sprites(
    @Json(name = "front_default")
    val frontDefault: String? = null,
    val other: Other? = null
) {
    @JsonClass(generateAdapter = true)
    data class Other(
        @Json(name = "official-artwork")
        val officialArtwork: OfficialArtwork
    ) {
        @JsonClass(generateAdapter = true)
        data class OfficialArtwork(
            @Json(name = "front_default")
            val frontDefault: String? = null
        )
    }
}
