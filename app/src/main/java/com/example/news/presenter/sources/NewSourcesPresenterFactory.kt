package com.example.news.presenter.sources

import android.content.Context
import com.example.news.datastore.DataStore
import com.example.news.datastore.repository.NewsRepositoryImpl
import com.example.news.view.everything.NewsSourcesView
import io.reactivex.schedulers.Schedulers

class NewSourcesPresenterFactory {
    companion object{
        fun createPresenter(context: Context, view: NewsSourcesView): SourcesPresenter {
            val newsAPI = DataStore.createNewAPI(context)
            val newsRepository = NewsRepositoryImpl(Schedulers.io(), newsAPI)
            return SourcesPresenterImpl(
                newsRepository,
                view
            )
        }
    }
}