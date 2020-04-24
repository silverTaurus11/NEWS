package com.example.news.view

import android.annotation.SuppressLint
import android.net.http.SslError
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.news.R
import kotlinx.android.synthetic.main.activity_web_detail.*


class WebViewActivity: AppCompatActivity() {
    companion object{
        const val URL_STRING_KEY = "urlStringKey"
        const val TITLE_STRING_KEY = "titleStringKey"
        const val MAX_PROGRESS = 100
    }

    private var articleUrl = ""
    private var articleTitle = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_detail)
        initWebView()
        setWebClient()
        initListener()
        requestData()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        webView.settings.javaScriptEnabled = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
        webView.settings.domStorageEnabled = true

        webView.webViewClient = object : WebViewClient() {
            override
            fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                handler?.proceed()
            }

            override fun onReceivedError(
                view: WebView?,
                errorCode: Int,
                description: String?,
                failingUrl: String?
            ) {
                super.onReceivedError(view, errorCode, description, failingUrl)
                showErrorLayout()
            }

        }
    }

    private fun setWebClient() {
        webView.webChromeClient = object : WebChromeClient() {

            override fun onProgressChanged(
                view: WebView,
                newProgress: Int
            ) {
                super.onProgressChanged(view, newProgress)
                progressBar.progress = newProgress
                if (newProgress < MAX_PROGRESS && progressBar.visibility == ProgressBar.GONE) {
                    progressBar.visibility = ProgressBar.VISIBLE
                }

                if (newProgress == MAX_PROGRESS) {
                    progressBar.visibility = ProgressBar.GONE
                }
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun requestData(){
        articleUrl = intent.getStringExtra(URL_STRING_KEY)
        articleTitle = intent.getStringExtra(TITLE_STRING_KEY)

        if(articleUrl.isNotBlank()){
            loadUrl(articleUrl, articleTitle)
        }
    }

    private fun loadUrl(pageUrl: String, title:String) {
        error_layout.visibility = View.GONE
        webview_container.visibility = View.VISIBLE

        webView.loadUrl(pageUrl)
        if(title.isNotBlank()){
            web_detail_title.text = title
        }
    }

    private fun showErrorLayout(){
        error_layout.visibility = View.VISIBLE
        webview_container.visibility = View.GONE
    }

    private fun initListener(){
        retry_button.setOnClickListener { requestData() }
        close_button.setOnClickListener { finish() }
        webView.viewTreeObserver.addOnScrollChangedListener {
            if (webView.scrollY == webView.maximumScrollValue) {
                Toast.makeText(applicationContext, resources.getString(R.string.has_reached_bottom_list),
                    Toast.LENGTH_LONG).show()
            }
        }
    }

}