package com.example.colorpage

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class JsonParse {
    fun getColorJson(data : String) : ArrayList<ColorDataMember> {
            val colorDataMemberList: ArrayList<ColorDataMember> =
                ArrayList<ColorDataMember>()
            val Array : JSONArray = JSONArray(data)
            for (i in 0..(Array.length()-1)) {
                val jsonObject : JSONObject = Array.getJSONObject(i)
                val id : String = jsonObject.getString("id")
                val title : String = jsonObject.getString("title")
                val url : String = jsonObject.getString("url")
                val thumbnailUrl : String = jsonObject.getString("thumbnailUrl")
                colorDataMemberList.add(ColorDataMember(id, title, url, thumbnailUrl))
                }

            return colorDataMemberList
    }
}