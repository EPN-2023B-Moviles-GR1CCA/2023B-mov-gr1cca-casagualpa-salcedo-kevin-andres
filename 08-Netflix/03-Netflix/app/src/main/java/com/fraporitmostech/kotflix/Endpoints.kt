package com.fraporitmostech.kotflix

import retrofit2.Response
import retrofit2.http.GET

interface Endpoints {
    @GET("/FranEspino/demo/db")
    suspend  fun getDataMovies(): Response<MovieResponse>
}