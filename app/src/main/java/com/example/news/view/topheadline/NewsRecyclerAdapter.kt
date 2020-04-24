package com.example.news.view.topheadline

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.helper.Helper
import com.example.news.model.ArticleItem

class NewsRecyclerAdapter(private val newsViewHolderListener: NewsViewHolderListener): RecyclerView.Adapter<NewsViewHolder>() {
    private var articles : List<ArticleItem> = arrayListOf()

    fun updateItems(articles: List<ArticleItem>){
        this.articles = articles
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_preview_item, parent, false)
        return NewsViewHolder(view, newsViewHolderListener)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(articles[position])
    }
}

class NewsViewHolder(view: View, private val listener: NewsViewHolderListener): RecyclerView.ViewHolder(view){
    private val titleLabel by lazy { view.findViewById<TextView>(R.id.news_title) }
    private val sourceLabel by lazy { view.findViewById<TextView>(R.id.news_source) }
    private val dateLabel by lazy { view.findViewById<TextView>(R.id.news_date) }
    private val newsContainer by lazy { view.findViewById<LinearLayout>(R.id.news_container) }

    fun bind(articleItem: ArticleItem) {
        titleLabel.text = articleItem.title
        sourceLabel.text = articleItem.source.name
        dateLabel.text = Helper.convertAndPrintDate(articleItem.publishedAt)

        newsContainer.setOnClickListener {
            listener.onItemClickListener(articleItem)
        }
    }
}

open interface NewsViewHolderListener{
    fun onItemClickListener(item: ArticleItem)
}