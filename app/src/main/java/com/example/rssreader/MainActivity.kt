package com.example.rssreader

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import androidx.core.os.HandlerCompat
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    lateinit var adapter: RowModelAdapter
    lateinit var handler: Handler
    lateinit var executorService: ExecutorService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainLooper = mainLooper
        handler = HandlerCompat.createAsync(mainLooper)
        executorService = Executors.newSingleThreadExecutor()
        adapter = RowModelAdapter(this)
        val intent = intent
        val categoryStr = intent.getStringExtra("CATEGORY")
        var categoryURL: String? = null
        when (categoryStr) {
            "スポーツ" -> categoryURL = "sports"
            "芸能" -> categoryURL = "entertainment"
            "経済" -> categoryURL = "business"
            else -> categoryURL = "it"
        }

        var list = findViewById<ListView>(R.id.resultList)
        list.adapter = adapter
        val uriBuilder = Uri.Builder()
        uriBuilder.scheme("https")
        uriBuilder.authority("jec-cm-linux2020.lolipop.io")
        uriBuilder.path("test.php")
        uriBuilder.appendQueryParameter(
            "url","https://news.yahoo.co.jp/rss/categories/${categoryURL}.xml")
        Log.i("MainActivity", uriBuilder.build().toString())
        val asyncHttpRequest = AsyncHttpRequest(handler, this@MainActivity, uriBuilder.toString())
        executorService.submit(asyncHttpRequest)
    }

    // データの一覧をリストなどのビューに渡すために使用されるクラス
        inner class RowModelAdapter(context: Context): ArrayAdapter<RssItem>(context, R.layout.row_item) {
         override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
             val item = getItem(position) as RssItem
             Log.e("MainMain", "format" + item.pubDate)
             var convertView = convertView
             var inflater = layoutInflater
                 convertView = inflater.inflate(R.layout.row_item, null)
//             val rssItem = list.[position]
             if (item != null) {
                 val txtTitle = convertView?.findViewById<TextView>(R.id.txtTitle)
                 if (txtTitle != null) {
                     txtTitle.text = item.title
                 }
                 val txtLink = convertView?.findViewById<TextView>(R.id.txtLink)
                 if (txtLink != null) {
                     txtLink.text = item.link
                 }
                 val txtPubData = convertView?.findViewById<TextView>(R.id.txtPubDate)
                 if (txtPubData != null) {
                     Log.i("MainMain", "format" + item.pubDate)
                     val inputFormat = "EEE, dd MMM yyyy HH:mm:ss z"
                     val outputFormat = "yyyy/MM/dd(EEE) HH:mm:ss'更新'"

                     val dateFormat = SimpleDateFormat(inputFormat, Locale.US)
                     val date = dateFormat.parse(item.pubDate)

                     val outputDateFormat = SimpleDateFormat(outputFormat, Locale.JAPAN)
                     val outputDate = outputDateFormat.format(date)
                     txtPubData.text = outputDate
                 }
                 val txtAcquisitionNews = findViewById<TextView>(R.id.txtPubDate2)
                 if (txtAcquisitionNews != null) {
                     val inputFormat = "EEE, dd MMM yyyy HH:mm:ss z"
                     val outputFormat = "yyyy/MM/dd(EEE) HH:mm:ss'更新'"

                     val dateFormat = SimpleDateFormat(inputFormat, Locale.US)
                     val date = dateFormat.parse(item.pubDates)

                     val outputDateFormat = SimpleDateFormat(outputFormat, Locale.JAPAN)
                     val outputDate = outputDateFormat.format(date)
                     txtAcquisitionNews.text = outputDate
                 }
                 val btnJumpPage = convertView?.findViewById<Button>(R.id.btnJumpPage)
                 btnJumpPage?.setOnClickListener {
                     val intent = Intent(applicationContext, WebviewActivity::class.java)
                     intent.putExtra("URL", item.link)
                     startActivity(intent)
                 }
             }
             return convertView
         }
    }
}