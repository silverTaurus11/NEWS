package com.example.news.model

import com.google.gson.annotations.SerializedName

open class BaseSourceItem{
    @SerializedName("id")
    var id: String?= ""

    @SerializedName("name")
    var name: String?= ""

}