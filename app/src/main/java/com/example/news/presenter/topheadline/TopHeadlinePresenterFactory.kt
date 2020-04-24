package com.example.news.presenter.topheadline

import android.content.Context
import com.example.news.datastore.DataStore
import com.example.news.datastore.repository.NewsRepositoryImpl
import com.example.news.view.topheadline.ArticleView
import io.reactivex.schedulers.Schedulers

class TopHeadlinePresenterFactory {
    companion object{
        fun createPresenter(context: Context, articleView: ArticleView): TopHeadlinePresenter {
            val newsAPI = DataStore.createNewAPI(context)
            val newsRepository = NewsRepositoryImpl(Schedulers.io(), newsAPI)
            return TopHeadlinePresenterImpl(
                newsRepository,
                articleView
            )
        }
    }
}