package com.example.news.model

import com.google.gson.annotations.SerializedName

data class SourceItem(
    @SerializedName("description") var description: String,
    @SerializedName("country") var country: String,
    @SerializedName("category") var category: String,
    @SerializedName("language") var language: String,
    @SerializedName("url") var url: String
): BaseSourceItem()