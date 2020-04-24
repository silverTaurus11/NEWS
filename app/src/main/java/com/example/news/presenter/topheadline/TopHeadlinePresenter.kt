package com.example.news.presenter.topheadline

interface TopHeadlinePresenter {
    fun requestEverything(filter: Map<String, String>)
    fun requestTopHeadline(filter: Map<String, String>)
    fun onStop()
}