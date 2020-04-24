package com.example.news.presenter.sources

interface SourcesPresenter {
    fun requestSources(filter: Map<String, String>)
    fun requestArticles(filter: Map<String, String>)
    fun onStop()
}