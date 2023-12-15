package com.example.testapi.data.retrofit

import com.example.testapi.data.response.DetailResponse
import com.example.testapi.data.response.ItemsItem
import com.example.testapi.data.response.TestResponse
import okhttp3.Interceptor
import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getApis(
        @Query("q") q: String,
        @Header("Authorization") token: String = AUTH
    ): Call<TestResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String,
        @Header("Authorization") token: String = AUTH
    ): Call<DetailResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String,
        @Header("Authorization") token: String = AUTH
    ) : Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String,
        @Header("Authorization") token: String = AUTH
    ) : Call<List<ItemsItem>>



    companion object {
        const val AUTH = "token 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"
    }
}
