package com.example.news.model

import com.google.gson.annotations.SerializedName

data class ArticlesResponse(
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("articles") val articles: List<ArticleItem>
){
    companion object{
        val STATUS_OK = "ok"
    }
}