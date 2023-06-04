package com.example.rssreader

import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception

class JsonHelper {
    companion object {
        fun parseJson(strJson: String): ArrayList<RssItem> {
            val arrayList = ArrayList<RssItem>()
            try {
                val jsonObject = JSONObject(strJson)
                val channel = jsonObject.getJSONObject("channel")
                val itemArray = channel.getJSONArray("item")
                for (item in 0 until itemArray.length() ) {
                    val entry = itemArray.getJSONObject(item)
                    arrayList.add(parseToItem(entry))
                }
            } catch (e: Exception) {

            }
            return arrayList
        }

        @Throws(JSONException::class)
        fun parseToItem(json: JSONObject) : RssItem {
            val tmp = RssItem()
            tmp.title = json.getString("title")
            tmp.link = json.getString("link")
            return tmp
        }

    }
}