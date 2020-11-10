package com.example.colorpage

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.IOException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class JSONUtils {

    fun getColorJson(APIurl: String): String {
        val datas: String
        println(APIurl)
        try {
            val url = URL(APIurl)
            val con = url.openConnection() as HttpsURLConnection
            datas = con.inputStream.bufferedReader().readText()
            return datas

        } catch (ex: Exception) {
            Log.d("Exception", ex.toString())
        }

        return ""
    }

    fun getImage(APIurl: String) : Bitmap? {
        try {
            var url = URL(APIurl)
            val con = url.openConnection() as HttpsURLConnection
            con.doOutput
            con.connect()
            val input = con.inputStream
            val bitmap = BitmapFactory.decodeStream(input)
            input.close()


            return bitmap
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}