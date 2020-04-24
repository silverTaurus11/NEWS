package com.example.news.view.topheadline

import com.example.news.model.ArticleItem

interface ArticleView {
    fun showLoading()
    fun showData(articles: List<ArticleItem>)
    fun showNoInternetConnection()
    fun showGeneralError()
}