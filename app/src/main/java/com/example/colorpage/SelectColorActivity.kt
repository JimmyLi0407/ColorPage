package com.example.colorpage

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader


class SelectColorActivity : AppCompatActivity(){
    private val FileName :String = "ColorData"
    private var ColorData : StringBuffer = StringBuffer()
    private var JsonParse = JsonParse()
    private var ColorDataList : ArrayList<ColorDataMember> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_color)


    }

    override fun onStart() {
        super.onStart()
        ColorData.append(ReadFile(FileName))
        ColorDataList = JsonParse.getColorJson(ColorData.toString())
        initview()
    }

    private fun initview() {

        val recyclerView = findViewById<RecyclerView>(R.id.list_view)
        val gridLayoutManager = GridLayoutManager(this, 4)
        val colorAdapter = ColorAdapter(this, ColorDataList)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = colorAdapter


    }

    private fun ReadFile(filename: String): StringBuffer? {
        val str: StringBuffer = StringBuffer()
        try {
            val `is` = openFileInput(filename)
            if (`is` != null) {
                val reader = BufferedReader(InputStreamReader(`is`))
                while (reader.ready()) {
                    str.append(reader.readLine())
                }
                `is`.close()
            }
        } catch (e: FileNotFoundException) {
        } catch (t: Throwable) {
            Log.d("Error:", t.toString())
        }
        return str
    }

    class ColorAdapter : RecyclerView.Adapter<ColorAdapter.viewHolder> {
        private var context : Context
        private var colordata : ArrayList<ColorDataMember>
        private val jSONUtils : JSONUtils = JSONUtils()


        constructor(context: Context, colordata : ArrayList<ColorDataMember>) : super() {
            this.context = context
            this.colordata = colordata
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder{

            val cell = LayoutInflater.from(context).inflate(R.layout.color_item, parent, false)
            val viewHolder = viewHolder(cell)
            viewHolder.ivImage = cell.findViewById(R.id.ivImage)
            viewHolder.tvid = cell.findViewById(R.id.tvid)
            viewHolder.tvTitle = cell.findViewById(R.id.tvTitle)
            return viewHolder
        }

        override fun getItemCount(): Int {
            return colordata.size
        }

        override fun onBindViewHolder(holder: viewHolder, position: Int) {
            val model = colordata[position]
            var bitmap : Bitmap?
            val colornum = model.thumbnailUrl.substringAfterLast("/")

            holder.tvid.setText(model.id)

            holder.tvTitle.setText(model.title)

            var LoadColorData = Thread(Runnable {

                if (colornum.length == 5) {
                    bitmap = jSONUtils.getImage("https://ipsumimage.appspot.com/150," + colornum + "0")
                } else {
                    bitmap = jSONUtils.getImage("https://ipsumimage.appspot.com/150," + colornum)
                }

               holder.ivImage.post {
                   holder.ivImage.setImageBitmap(bitmap)
               }

            })

            LoadColorData.start()

            try {
                LoadColorData.join()
            } catch (ex : Exception) {
                ex.printStackTrace()
            }

            holder.itemView.setOnClickListener(View.OnClickListener {
                val intent = Intent(context, thirdColorActivity::class.java)
                intent.putExtra("id", model.id)
                intent.putExtra("title", model.title)
                intent.putExtra("colornum", colornum)
                context.startActivity(intent)
            })
        }



        class viewHolder : RecyclerView.ViewHolder {
            lateinit var ivImage : ImageView
            lateinit var tvid : TextView
            lateinit var tvTitle : TextView


            constructor(itemview : View) :super(itemview)
        }
    }
}
