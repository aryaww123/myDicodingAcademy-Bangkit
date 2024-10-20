package com.dicoding.myview_app

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val imgPhoto: ImageView = findViewById(R.id.img_about_photo)
        val tvName: TextView = findViewById(R.id.tv_about_name)
        val tvEmail: TextView = findViewById(R.id.tv_about_email)

        imgPhoto.setImageResource(R.drawable.croppedimg)
        tvName.text = "Arya Wahyu Wibowo"
        tvEmail.text = "aryaww115@gmail.com"
    }
}