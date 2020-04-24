package com.example.news.view.everything

import com.example.news.model.ArticleItem
import com.example.news.model.SourceItem

interface NewsSourcesView {
    fun showData(sources: List<SourceItem>)
    fun showNoInternetConnection()
    fun showGeneralError()
    fun showArticles(articles: List<ArticleItem>)
    fun showLoading()
}