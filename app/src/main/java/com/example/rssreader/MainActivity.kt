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
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.HandlerCompat
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

        var list = findViewById<ListView>(R.id.resultList)
        list.adapter = adapter
        list.setOnItemClickListener { adapterView, view, position, id ->
            // ここにリストの項目がクリックされたときの処理を書く
            val item = adapterView.adapter.getItem(position) as RssItem
            Toast.makeText(this@MainActivity, item.link, Toast.LENGTH_SHORT).show()
            val uri = Uri.parse(item.link)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        val uriBuilder = Uri.Builder()
        uriBuilder.scheme("https")
        uriBuilder.authority("jec-cm-linux2020.lolipop.io")
        uriBuilder.path("test.php")
        uriBuilder.appendQueryParameter(
            "url","https://news.yahoo.co.jp/rss/categories/sports.xml")
        Log.i("MainActivity", uriBuilder.build().toString())
        val asyncHttpRequest = AsyncHttpRequest(handler, this@MainActivity, uriBuilder.toString())
        executorService.submit(asyncHttpRequest)
    }

//    private fun getData(): String {
//        val inputStream = resources.assets.open("rss.json")
//        val jsonString = inputStream.bufferedReader().use { it.readText() }
//        return jsonString
//    }

        inner class RowModelAdapter(context: Context): ArrayAdapter<RssItem>(context, R.layout.row_item) {
         override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
             val item = getItem(position) as RssItem
             var convertView = convertView
                 var inflater = layoutInflater
                 convertView = inflater.inflate(R.layout.row_item, null)

             if (item != null) {
                 val txtTitle = convertView?.findViewById<TextView>(R.id.txtTitle)
                 if (txtTitle != null) {
                     txtTitle.text = item.title
                 }
                 val txtLink = convertView?.findViewById<TextView>(R.id.txtLink)
                 if (txtLink != null) {
                     txtLink.text = item.link
                 }
             }
             return convertView
         }
    }
}