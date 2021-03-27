package com.mr.retrofittest2

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface retrofitService {

    @GET("search")
    suspend fun getAlbums(@Query("query") query:String,@Query("page") page:Int=1,@Query("per_page") perPage:Int=9): Response<Album>

}