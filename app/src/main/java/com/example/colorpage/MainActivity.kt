package com.example.colorpage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var JSONUtils = JSONUtils()
    private var ColorData = StringBuffer()
    private val FileName :String = "ColorData"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val LoadColorData = Thread(Runnable {
            ColorData.append(JSONUtils.getColorJson("https://jsonplaceholder.typicode.com/photos"))
        })

        LoadColorData.start()

        try {
            LoadColorData.join()
        } catch (ex : Exception) {
            ex.printStackTrace()
        }

        writeFile(ColorData.toString(), FileName)

        btRequest.setOnClickListener {
            val intent = Intent(this@MainActivity, SelectColorActivity::class.java)
            startActivity(intent)
        }

    }

    private fun writeFile(str: String, filename: String) {
        try {
            val fos = openFileOutput(filename, Context.MODE_APPEND)
            fos.write(str.toByteArray())
            fos.close()
        } catch (t: Throwable) {
            Log.d("Error:", t.toString())
        }
    }
}
