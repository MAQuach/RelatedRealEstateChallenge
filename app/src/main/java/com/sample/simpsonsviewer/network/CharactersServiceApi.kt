package com.sample.simpsonsviewer.network

import com.sample.simpsonsviewer.BuildConfig
import com.sample.simpsonsviewer.domain.apimodel.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// http://api.duckduckgo.com/?q=simpsons+characters&format=json
// http://api.duckduckgo.com/?q=the+wire+characters&format=json

const val BASE_URL = "http://api.duckduckgo.com/"
private const val PARAM1 = BuildConfig.CHARACTER
private const val PARAM2 = "characters"
private const val FORMAT = "json"

interface CharactersServiceApi {

    @GET("/")
    suspend fun getAllCharacters(
        @Query("q") q: String = "$PARAM1 $PARAM2",
        @Query("format") format: String = FORMAT
    ): Response<CharacterResponse>
}
