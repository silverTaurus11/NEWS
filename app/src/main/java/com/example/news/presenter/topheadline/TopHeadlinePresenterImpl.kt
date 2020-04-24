package com.example.news.presenter.topheadline

import android.annotation.SuppressLint
import android.util.Log
import com.example.news.datastore.repository.NewsRepository
import com.example.news.model.ArticlesResponse
import com.example.news.view.topheadline.ArticleView
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

class TopHeadlinePresenterImpl(private val newsRepository: NewsRepository,
                               private val articleView: ArticleView):
    TopHeadlinePresenter {
    private val TAG = "TopHeadlinePresenterImpl"
    private lateinit var disposable: Disposable
    private lateinit var secondDisposable: Disposable

    @SuppressLint("LongLogTag")
    override fun requestEverything(filter: Map<String, String>) {
        articleView.showLoading()
        secondDisposable = newsRepository
            .getArticlesByEverything(filter)
            .observeOn(Schedulers.computation())
            .subscribe(
                {
                    if(ArticlesResponse.STATUS_OK.equals(it.status)){
                        articleView.showData(it.articles)
                    } else{
                        articleView.showGeneralError()
                    }
                },{
                    if(it is IOException){
                        articleView.showNoInternetConnection()
                    } else{
                        articleView.showGeneralError()
                    }
                })
    }

    @SuppressLint("LongLogTag")
    override fun requestTopHeadline(filter: Map<String, String>) {
        articleView.showLoading()
        disposable = newsRepository
            .getArticlesByTopHeadline(filter)
            .observeOn(Schedulers.computation())
            .subscribe(
                {
                    if(ArticlesResponse.STATUS_OK.equals(it.status)){
                        articleView.showData(it.articles)
                    } else{
                        articleView.showGeneralError()
                    }
                },{
                    if(it is IOException){
                        articleView.showNoInternetConnection()
                    } else{
                        articleView.showGeneralError()
                    }
                })
    }

    override fun onStop() {
        disposable?.dispose()
        secondDisposable?.dispose()
    }
}