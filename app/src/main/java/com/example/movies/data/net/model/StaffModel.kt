package com.example.movies.data.net.model

import com.google.gson.annotations.SerializedName

data class StaffModel(
    @SerializedName("staffId")
    val id: Long,
    @SerializedName("posterUrl")
    val image: String,
    @SerializedName("nameRu")
    val name: String?,
    @SerializedName("description")
    val character: String?,
    @SerializedName("professionText")
    val profession: String
)