package com.example.news.datastore.repository

import com.example.news.model.ArticlesResponse
import com.example.news.model.SourcesResponse
import io.reactivex.Observable

interface NewsRepository {
    fun getArticlesBySource(filters: Map<String, String>): Observable<SourcesResponse>
    fun getArticlesByTopHeadline(filters: Map<String, String>): Observable<ArticlesResponse>
    fun getArticlesByEverything(filters: Map<String, String>): Observable<ArticlesResponse>
}