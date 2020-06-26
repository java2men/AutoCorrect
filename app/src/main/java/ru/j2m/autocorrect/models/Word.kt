package ru.j2m.autocorrect.models

import com.google.gson.annotations.SerializedName

data class Word(
    @SerializedName("original") val original: String,
    @SerializedName("to") val to: String
)