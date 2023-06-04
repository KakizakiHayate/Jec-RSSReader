package com.example.rssreader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text
import java.io.BufferedReader
import java.io.IOError
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnLoad = findViewById<Button>(R.id.btnLoad)
        btnLoad.setOnClickListener {
            var sb = StringBuilder()
            val arrayList = JsonHelper.parseJson(getData())
            for (tmp in arrayList ) {
                sb.append(tmp.title + "\n")
                sb.append(tmp.link + "\n")
            }
            val txtResult = findViewById<TextView>(R.id.txtResult)
            txtResult.text = sb.toString()
        }
    }

    private fun getData(): String {
        val inputStream = resources.assets.open("rss.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        return jsonString
    }
}