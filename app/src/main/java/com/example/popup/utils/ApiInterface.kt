package com.example.popup.utils

import com.example.popup.model.Person
import com.example.popup.model.items
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface ApiInterface {

    @POST("register")
    fun register(@Body person: Person?): Call<Person>

    @POST("login")
    fun login(@Body person: Person?): Call<Person>

    @POST("buy")
    fun buy(@Body itm: items?): Call<items>

    companion object {

        var BASE_URL = "http://0.0.0.0:5000/"

        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }

//    @FormUrlEncoded
//    @POST("comments")
//    fun createComment(
//        @Field("title") title: String?,
//        @Field("comment") comment: String?,
//        @Field("author") author: String?
//    ): Call<Comment?>?

//    @FormUrlEncoded
//    @POST("comments")
//    fun createComment(@FieldMap fields: Map<String?, String?>?): Call<Comment?>?

}