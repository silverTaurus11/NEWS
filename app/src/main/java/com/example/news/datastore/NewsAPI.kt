package com.example.news.datastore

import com.example.news.model.ArticlesResponse
import com.example.news.model.SourcesResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap

interface NewsAPI{
    @Headers("Content-Type: application/json")
    @GET("/v2/sources")
    fun getArticlesBySource(
        @QueryMap filters: Map<String, String>): Observable<SourcesResponse>

    @Headers("Content-Type: application/json")
    @GET("/v2/top-headlines")
    fun getArticlesByTopHeadline(
        @QueryMap filters: Map<String, String>): Observable<ArticlesResponse>

    @Headers("Content-Type: application/json")
    @GET("/v2/everything")
    fun getArticlesByEverything(
        @QueryMap filters: Map<String, String>): Observable<ArticlesResponse>

}