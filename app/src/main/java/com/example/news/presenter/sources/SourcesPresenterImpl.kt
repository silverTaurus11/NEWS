package com.example.news.presenter.sources

import android.util.Log
import com.example.news.datastore.repository.NewsRepository
import com.example.news.model.ArticlesResponse
import com.example.news.view.everything.NewsSourcesView
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.IOException

class SourcesPresenterImpl(private val newsRepository: NewsRepository,
                           private val newsSourcesView: NewsSourcesView): SourcesPresenter {
    private val TAG = "SourcesPresenterImpl"
    private lateinit var disposable: Disposable
    private lateinit var secondDisposable: Disposable

    override fun requestSources(filter: Map<String, String>) {
        newsSourcesView.showLoading()
        disposable = newsRepository
            .getArticlesBySource(filter)
            .observeOn(Schedulers.computation())
            .subscribe(
                {
                    if(ArticlesResponse.STATUS_OK.equals(it.status)){
                        newsSourcesView.showData(it.sources)
                    } else{
                        newsSourcesView.showGeneralError()
                    }
                },{
                    if(it is IOException){
                        newsSourcesView.showNoInternetConnection()
                    } else{
                        newsSourcesView.showGeneralError()
                    }
                })
    }

    override fun requestArticles(filter: Map<String, String>) {
        newsSourcesView.showLoading()
        secondDisposable = newsRepository
            .getArticlesByEverything(filter)
            .observeOn(Schedulers.computation())
            .subscribe(
                {
                    if(ArticlesResponse.STATUS_OK.equals(it.status)){
                        newsSourcesView.showArticles(it.articles)
                    } else{
                        newsSourcesView.showGeneralError()
                    }
                },{
                    if(it is IOException){
                        newsSourcesView.showNoInternetConnection()
                    } else{
                        newsSourcesView.showGeneralError()
                    }
                })
    }

    override fun onStop() {
        disposable.dispose()
        secondDisposable.dispose()
    }
}