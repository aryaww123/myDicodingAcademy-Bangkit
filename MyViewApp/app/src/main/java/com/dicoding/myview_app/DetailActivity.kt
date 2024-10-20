package com.dicoding.myview_app

import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.Person
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

class DetailActivity : AppCompatActivity() {

    companion object {
        const val KEY_IPHONE = "key_iphone"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val dataIphone = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<Iphone>(KEY_IPHONE, Iphone::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Iphone>(KEY_IPHONE)
        }

        val imgPhoto: ImageView = findViewById(R.id.img_detail_photo)
        val tvName: TextView = findViewById(R.id.tv_detail_name)
        val tvDescription: TextView = findViewById(R.id.tv_detail_description)

        imgPhoto.setImageResource(dataIphone!!.photo)
        tvName.text = dataIphone.name
        tvDescription.text = dataIphone.description
    }
}