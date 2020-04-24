package com.example.news.datastore

import android.content.Context
import com.example.news.R
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object DataStore{
    fun createNewAPI(context: Context): NewsAPI{
        val baseUrl = context.resources.getString(R.string.news_api_host)
        val client = OkHttpClient.Builder()
            .addNetworkInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Accept", "Application/JSON")
                    .addHeader("X-Api-Key", context.resources.getString(R.string.news_api_key))
                    .build()
                chain.proceed(request)
            }.build()

        return create(baseUrl, client)
    }

    private fun create(baseUrl: String, client: OkHttpClient): NewsAPI {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()

        return retrofit.create(NewsAPI::class.java)
    }
}