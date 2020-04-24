package com.example.news.view.everything

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.model.SourceItem

class SourceRecyclerAdapter(private val newsViewHolderListener: NewsViewHolderListener): RecyclerView.Adapter<NewsViewHolder>() {
    private var articles : List<SourceItem> = arrayListOf()

    fun updateItems(articles: List<SourceItem>){
        this.articles = articles
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.source_item, parent, false)
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
    private val urlLabel by lazy { view.findViewById<TextView>(R.id.news_source_url) }
    private val newsContainer by lazy { view.findViewById<LinearLayout>(R.id.news_container) }

    fun bind(articleItem: SourceItem) {
        titleLabel.text = articleItem.description
        sourceLabel.text = articleItem.name
        urlLabel.text = articleItem.url

        newsContainer.setOnClickListener {
            listener.onItemClickListener(articleItem)
        }
    }
}

open interface NewsViewHolderListener{
    fun onItemClickListener(item: SourceItem)
}