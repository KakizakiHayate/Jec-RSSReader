package com.example.rssreader

import android.util.Log
import android.widget.ListView
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class AsyncHttpRequest(handler: android.os.Handler, mainActivity: MainActivity, urlString: String): Runnable {
    private var handler: android.os.Handler
    private var mainActivity: MainActivity
    private var urlString: String
    private lateinit var resString: String

    init {
        this.handler = handler
        this.mainActivity = mainActivity
        this.urlString = urlString
    }

    override fun run() {
        Log.i("RssReader,","BackgroundTask start…")
        // バックグラウンド(非同期)で実行する処理
        // Http通信
        resString = "取得に失敗しました"
        var connection: HttpsURLConnection? = null
        try {
            // 接続先webサイトのURLの文字列をURLクラスのオブジェクトにする
            val url = URL(urlString)
            // 接続先webサイトへの接続を開始
            connection = url.openConnection() as HttpsURLConnection
            connection.requestMethod = "GET"
            connection.connect()
            // 接続先から取得したInputStreamを文字列データにする
            Log.i("RssReaderOriginal","BackgroundTask finish…" + resString)
            resString = inputStreamToString(connection.inputStream)
            Log.i("RssReaderOriginal","BackgroundTask finish…" + resString)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("RssReaderOriginal", e.toString())
        }finally {
            if (connection != null) {
                Log.i("RssReaderOriginal","finally inininininin")
                connection.disconnect()
            }
            Log.i("RssReaderOriginal","finally outoutoutoutout")
            Log.i("RssReaderOriginal","BackgroundTask finish…" + resString)
        }
        handler.post { onPostExecute() }
    }

    @Throws(IOException::class)
    private fun inputStreamToString(inputStream: InputStream): String {
        val br = BufferedReader(InputStreamReader(inputStream))
        val sb = StringBuilder()
        var line: String?
        while (br.readLine().also { line = it } != null) {
            sb.append(line).append("\n")
        }
        br.close()
        return sb.toString()
    }

    private fun onPostExecute() {
        Log.i("RssReader", "onPostExecute start…")
        // 非同期処理後に実行する処理
        val arrayList = JsonHelper.parseJson(resString)
        for (tmp in arrayList) {
            mainActivity.adapter.add(tmp)
        }
        val list = mainActivity.findViewById<ListView>(R.id.resultList)
        list.adapter = mainActivity.adapter
    }
}