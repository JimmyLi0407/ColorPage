package com.example.colorpage

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_third_color.*

class thirdColorActivity : AppCompatActivity() {
    val jSONUtils : JSONUtils = JSONUtils()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_color)
        var id = intent.getStringExtra("id")
        var title = intent.getStringExtra("title")
        var colornum = intent.getStringExtra("colornum")
        var bitmap : Bitmap?



        var LoadColorData = Thread(Runnable {

            if (colornum.length == 5) {
                bitmap = jSONUtils.getImage("https://ipsumimage.appspot.com/600," + colornum + "0")
            } else {
                bitmap = jSONUtils.getImage("https://ipsumimage.appspot.com/600," + colornum)
            }

            ivImage.post {
                ivImage.setImageBitmap(bitmap)
            }

            tvId.post {
                tvId.setText("id: " + id)
            }

            tvTitle.setText("title: " + title)
        })

        LoadColorData.start()

        try {
            LoadColorData.join()
        } catch (ex : Exception) {
            ex.printStackTrace()
        }
    }
}
