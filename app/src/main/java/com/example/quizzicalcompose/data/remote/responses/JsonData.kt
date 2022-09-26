package com.example.quizzicalcompose.data.remote.responses

import com.google.gson.annotations.SerializedName

data class JsonData(
    @SerializedName("response_code")
    val responseCode: Int,
    val results: List<Result>
)