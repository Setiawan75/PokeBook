package com.world.pokebook.model.network

import com.world.pokebook.model.DetailPokemon
import com.world.pokebook.model.Pokemon
import com.world.pokebook.model.Result
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface APIEndpoint {
    @GET("pokemon")
    fun getListPokemon(): Call<Result>
    @GET
    fun getDetailPokemon(@Url url: String): Call<DetailPokemon>
}