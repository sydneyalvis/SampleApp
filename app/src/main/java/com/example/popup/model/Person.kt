package com.example.popup.model

import com.google.gson.annotations.SerializedName




class Person(
    @SerializedName("username") private var username: String?,
    @SerializedName("email") private var email: String?,
    @SerializedName("password") private var password: String?
) {

    fun getTitle(): String? {
        return username
    }

    fun getComment(): String? {
        return email
    }

    fun getAuthor(): String? {
        return password
    }

}