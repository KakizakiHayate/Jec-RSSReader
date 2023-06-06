package com.example.rssreader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView

class WebviewActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        val webview = findViewById<WebView>(R.id.webView)
        val intent = intent
        val urlStr = intent.getStringExtra("URL")
        webview.settings.javaScriptEnabled = true
        webview.loadUrl(urlStr!!)

    }
}