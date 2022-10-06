package com.example.apex.service.http

import com.example.apex.data.model.GetAddDateInformation
import com.example.apex.data.model.GetDiffDateInformation
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import io.reactivex.Single
import retrofit2.http.*

interface ApiServiceTiana {

    @GET("tiana/api/date")
    fun getDate(): Single<List<String>>

    @GET("tiana/api/date/diif")
    fun getDiffDate(@Query("fromDate")fromDate:String,@Query("toDate")toDate:String):Single<GetDiffDateInformation>

    @GET("tiana/api/date/AddDay")
    fun getAddDate(@Query("date")date:String,@Query("addDay")addDay:String):Single<GetAddDateInformation>
}

fun createApiServiceInstance(): ApiServiceTiana {

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            val oldRequest = it.request()
            val newRequestBuilder = oldRequest.newBuilder()
            newRequestBuilder.addHeader(
                "Authorization",
                "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6IjAuMTMzIiwiaXNzIjoiOTQuMTgzLjIuNzkiLCJhdWQiOiI5NC4xODMuMi43OSJ9.X1_vb4VTGEePtcLUL2OZ_TcHQDGOHYkT5D_r0l9bG34"
            )
            return@addInterceptor it.proceed(newRequestBuilder.build())
        }.build()

    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.20:19745/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()


    return retrofit.create(ApiServiceTiana::class.java)
}