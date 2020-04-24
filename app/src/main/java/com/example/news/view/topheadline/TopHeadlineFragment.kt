package com.example.news.view.topheadline

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.helper.CategoryConstanta
import com.example.news.model.ArticleItem
import com.example.news.presenter.topheadline.TopHeadlinePresenterFactory
import com.example.news.view.WebViewActivity
import org.w3c.dom.Text

class TopHeadlineFragment: Fragment(), ArticleView, View.OnClickListener {
    companion object{
        fun newInstance(): TopHeadlineFragment{
            return TopHeadlineFragment()
        }
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchEditText: EditText
    private lateinit var statusMessageView: TextView

    private val newsRecyclerAdapter by lazy { NewsRecyclerAdapter(object : NewsViewHolderListener{
        override fun onItemClickListener(articleItem: ArticleItem) {
            val intent = Intent(activity, WebViewActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            intent.putExtra(WebViewActivity.URL_STRING_KEY, articleItem.url)
            intent.putExtra(WebViewActivity.TITLE_STRING_KEY, articleItem.title)
            startActivity(intent)
        }
    }) }
    private val topHeadlinePresenter by lazy { TopHeadlinePresenterFactory.createPresenter(activity!!.applicationContext, this) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_top_headline, container, false) as ViewGroup
        recyclerView = view.findViewById(R.id.top_headline_list)
        searchEditText = view.findViewById(R.id.searchEditText)
        statusMessageView = view.findViewById(R.id.status_message_text)
        (view.findViewById(R.id.business_button) as TextView).setOnClickListener(this)
        (view.findViewById(R.id.entertainment_button) as TextView).setOnClickListener(this)
        (view.findViewById(R.id.general_button) as TextView).setOnClickListener(this)
        (view.findViewById(R.id.health_button) as TextView).setOnClickListener(this)
        (view.findViewById(R.id.science_button) as TextView).setOnClickListener(this)
        (view.findViewById(R.id.sports_button) as TextView).setOnClickListener(this)
        (view.findViewById(R.id.technology_button) as TextView).setOnClickListener(this)

        initRecycleView()
        initEditText()
        return view
    }

    private fun initRecycleView(){
        recyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)
        recyclerView.adapter = newsRecyclerAdapter
        val dividerItemDecoration = DividerItemDecoration(activity?.applicationContext, LinearLayout.HORIZONTAL)
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.setHasFixedSize(true)
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1) && dy > 0) {
                    Toast.makeText(activity, resources.getString(R.string.has_reached_bottom_list),
                        Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun initEditText(){
        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val text = searchEditText.text.toString()
                if(text.isNotBlank() && text.isNotEmpty()){
                    requestDataByQuery(searchEditText.text.toString())
                    searchEditText?.hideKeyboard()
                }
                true
            } else false
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requestData()
    }

    private fun requestDataByQuery(text: String){
        val data: MutableMap<String, String> = HashMap()
        data["q"] = text
        topHeadlinePresenter.requestEverything(data)
    }

    private fun requestData(){
        val data: MutableMap<String, String> = HashMap()
        data["country"] = "id"
        topHeadlinePresenter.requestTopHeadline(data)
    }

    private fun requestDataByCategori(category: String){
        val data: MutableMap<String, String> = HashMap()
        data["category"] = category
        topHeadlinePresenter.requestTopHeadline(data)
        searchEditText?.hideKeyboard()
    }

    override fun showData(articles: List<ArticleItem>) {
        activity?.runOnUiThread {
            if(articles.isNotEmpty()){
                showListLayout()
                newsRecyclerAdapter.updateItems(articles)
            } else{
                showDataNotFound()
            }
        }
    }

    override fun showNoInternetConnection() {
        activity?.runOnUiThread {
            Toast.makeText(activity, R.string.no_internet_connection_message, Toast.LENGTH_LONG).show()
            showGeneralError()
        }
    }

    override fun showGeneralError() {
        activity?.runOnUiThread {
            showMessageLayout()
            statusMessageView.text = resources.getString(R.string.cant_get_information)
        }
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.business_button -> requestDataByCategori(CategoryConstanta.BUSSINES)
            R.id.entertainment_button -> requestDataByCategori(CategoryConstanta.ENTERTAIMENT)
            R.id.general_button -> requestDataByCategori(CategoryConstanta.GENERAL)
            R.id.health_button -> requestDataByCategori(CategoryConstanta.HEALTH)
            R.id.science_button -> requestDataByCategori(CategoryConstanta.SCIENCE)
            R.id.sports_button -> requestDataByCategori(CategoryConstanta.SPORTS)
            R.id.technology_button -> requestDataByCategori(CategoryConstanta.TECHNOLOGY)
            else -> requestData()
        }
    }

    private fun showMessageLayout(){
        recyclerView.visibility = View.GONE
        statusMessageView.visibility = View.VISIBLE
    }

    private fun showListLayout(){
        recyclerView.visibility = View.VISIBLE
        statusMessageView.visibility = View.GONE
    }

    private fun showDataNotFound(){
        activity?.runOnUiThread {
            statusMessageView.text = resources.getString(R.string.data_not_found)
            showMessageLayout()
        }
    }

    override fun showLoading(){
        activity?.runOnUiThread {
            statusMessageView.text = resources.getString(R.string.wait_a_moment)
            showMessageLayout()
        }
    }
}