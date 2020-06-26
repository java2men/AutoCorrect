package ru.j2m.autocorrect.models

import com.google.gson.annotations.SerializedName

data class Dictionary(
    @SerializedName("name") val name: String,
    @SerializedName("words") val words: List<Word>
)