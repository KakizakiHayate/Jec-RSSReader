package com.example.rssreader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView

class WebviewActivity(url: String) : AppCompatActivity() {
    private var url: String
    init {
        this.url = url
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        val webview = findViewById<WebView>(R.id.webView)
        webview.settings.javaScriptEnabled = true
        webview.loadUrl(url)

    }
}