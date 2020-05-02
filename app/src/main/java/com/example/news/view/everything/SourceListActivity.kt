package com.example.news.view.everything

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.model.ArticleItem
import com.example.news.model.SourceItem
import com.example.news.presenter.sources.NewSourcesPresenterFactory
import com.example.news.view.WebViewActivity
import com.example.news.view.topheadline.NewsRecyclerAdapter
import kotlinx.android.synthetic.main.category_activity_source_list.*

class SourceListActivity: AppCompatActivity(), NewsSourcesView {
    companion object{
        const val SOURCES_STRING_KEY = "sourcesStringKey"
        const val CATEGORY_LABEL_KEY = "categoryLabelKey"
    }

    private val sourceRecyclerAdapter by lazy { SourceRecyclerAdapter(object : NewsViewHolderListener {
        override fun onItemClickListener(articleItem: SourceItem) {
            articleItem.id?.let { requestArticleBySources(it) }
        }
    }) }

    private val newsRecyclerAdapter by lazy { NewsRecyclerAdapter(object : com.example.news.view.topheadline.NewsViewHolderListener {
        override fun onItemClickListener(articleItem: ArticleItem) {
            val intent = Intent(applicationContext, WebViewActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            intent.putExtra(WebViewActivity.URL_STRING_KEY, articleItem.url)
            intent.putExtra(WebViewActivity.TITLE_STRING_KEY, articleItem.title)
            startActivity(intent)
        }
    }) }

    private val sourcesPresenter by lazy { NewSourcesPresenterFactory.createPresenter(this, this) }
    private var isRequestArticleMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.category_activity_source_list)
        initTitle()
        initRecycleView()
        requestSources()
    }

    private fun initRecycleView(){
        val dividerItemDecoration = DividerItemDecoration(this, LinearLayout.HORIZONTAL)

        sources_list.layoutManager = LinearLayoutManager(this)
        sources_list.adapter = sourceRecyclerAdapter
        sources_list.addItemDecoration(dividerItemDecoration)
        sources_list.setHasFixedSize(true)
        sources_list.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1) && dy > 0) {
                    Toast.makeText(applicationContext, resources.getString(R.string.has_reached_bottom_list),
                        Toast.LENGTH_LONG).show()
                }
            }
        })

        articles_list.layoutManager = LinearLayoutManager(this)
        articles_list.adapter = newsRecyclerAdapter
        articles_list.addItemDecoration(dividerItemDecoration)
        articles_list.setHasFixedSize(true)
        articles_list.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1) && dy > 0) {
                    Toast.makeText(applicationContext, resources.getString(R.string.has_reached_bottom_list),
                        Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun requestSources(){
        val sources = intent.getStringExtra(SOURCES_STRING_KEY)
        if(sources.isNotBlank()){
            val data: MutableMap<String, String> = HashMap()
            data["category"] = sources
            sourcesPresenter.requestSources(data)
        }
    }

    private fun initTitle(){
        title_text.text = intent.getStringExtra(CATEGORY_LABEL_KEY)
    }

    override fun showData(sources: List<SourceItem>) {
        runOnUiThread {
            if(sources.isNotEmpty()){
                sourceRecyclerAdapter.updateItems(sources)
                showSourceItemLayout()
            } else{
                showDataNotFound()
            }
        }
    }

    override fun showNoInternetConnection() {
        runOnUiThread {
            Toast.makeText(this, R.string.no_internet_connection_message, Toast.LENGTH_LONG).show()
            showGeneralError()
        }
    }

    override fun showGeneralError() {
        runOnUiThread {
            status_message_text.visibility = View.VISIBLE
            sources_list.visibility = View.GONE
            articles_list.visibility = View.GONE
            status_message_text.text = resources.getString(R.string.cant_get_information)
        }
    }

    override fun showArticles(articles: List<ArticleItem>) {
        runOnUiThread {
            if(articles.isNotEmpty()){
                newsRecyclerAdapter.updateItems(articles)
                sources_list.visibility = View.GONE
                articles_list.visibility = View.VISIBLE
                status_message_text.visibility = View.GONE
            } else{
                showDataNotFound()
            }
        }
    }

    private fun showSourceItemLayout(){
        sources_list.visibility = View.VISIBLE
        articles_list.visibility = View.GONE
        status_message_text.visibility = View.GONE
        isRequestArticleMode = false
    }

    override fun showLoading() {
        status_message_text.visibility = View.VISIBLE
        sources_list.visibility = View.GONE
        articles_list.visibility = View.GONE
        status_message_text.text = resources.getString(R.string.wait_a_moment)
    }

    private fun requestArticleBySources(sources: String) {
        val data: MutableMap<String, String> = HashMap()
        data["sources"] = sources
        sourcesPresenter.requestArticles(data)
        isRequestArticleMode = true
    }

    private fun showDataNotFound(){
        status_message_text.visibility = View.VISIBLE
        sources_list.visibility = View.GONE
        articles_list.visibility = View.GONE
        status_message_text.text = resources.getString(R.string.data_not_found)
    }


    override fun onBackPressed() {
        if(isRequestArticleMode){
            showSourceItemLayout()
        } else{
            super.onBackPressed()
        }
    }
}