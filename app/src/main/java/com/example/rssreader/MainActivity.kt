package com.example.rssreader

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text
import java.io.BufferedReader
import java.io.IOError
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: RowModelAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val btnLoad = findViewById<Button>(R.id.btnLoad)
//        btnLoad.setOnClickListener {
//            var sb = StringBuilder()
//            val arrayList = JsonHelper.parseJson(getData())
//            for (tmp in arrayList ) {
//                sb.append(tmp.title + "\n")
//                sb.append(tmp.link + "\n")
//            }
//            val txtResult = findViewById<TextView>(R.id.txtResult)
//            txtResult.text = sb.toString()
//        }
        adapter = RowModelAdapter(this)
        val arrayList = JsonHelper.parseJson(getData())
        for (item in arrayList) {
            adapter.add(item)
        }

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
    }

    private fun getData(): String {
        val inputStream = resources.assets.open("rss.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        return jsonString
    }


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
                 val txtlink = convertView?.findViewById<TextView>(R.id.txtLink)
                 if (txtlink != null) {
                     txtlink.text = item.link
                 }
             }
             return convertView
         }
    }
}