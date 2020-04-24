package com.example.news.datastore.repository

import com.example.news.datastore.NewsAPI
import com.example.news.model.ArticlesResponse
import com.example.news.model.SourcesResponse
import io.reactivex.Observable
import io.reactivex.Scheduler

class NewsRepositoryImpl(private val scheduler: Scheduler, private val newsAPI: NewsAPI) : NewsRepository{

    override fun getArticlesBySource(filters: Map<String, String>): Observable<SourcesResponse> {
        return newsAPI.getArticlesBySource(filters).subscribeOn(scheduler)
    }

    override fun getArticlesByTopHeadline(filters: Map<String, String>): Observable<ArticlesResponse> {
        return newsAPI.getArticlesByTopHeadline(filters).subscribeOn(scheduler)
    }

    override fun getArticlesByEverything(filters: Map<String, String>): Observable<ArticlesResponse> {
        return newsAPI.getArticlesByEverything(filters).subscribeOn(scheduler)
    }

}