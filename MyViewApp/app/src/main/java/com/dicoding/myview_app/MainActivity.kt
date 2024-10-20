package com.dicoding.myview_app

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var rvIphone: RecyclerView
    private val list = ArrayList<Iphone>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#095C9B")))

//        val toolbar = findViewById<Toolbar>(R.id.toolbar)
//        setSupportActionBar(toolbar)
//        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFFFFF")))


        rvIphone = findViewById(R.id.rv_iphone)
        rvIphone.setHasFixedSize(true)

        list.addAll(getListIphone())
        showRecyclerList()
    }

    private fun getListIphone(): ArrayList<Iphone>{
        val dataName= resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)

        val listIphone = ArrayList<Iphone>()

        for (i in dataName.indices){
            val iphone = Iphone(dataName[i], dataDescription[i], dataPhoto.getResourceId(i, -1))
            listIphone.add(iphone)
        }

        return listIphone
    }

    private fun showSelectedIphone(iphone: Iphone){
        Toast.makeText(this, "Kamu memilih "+ iphone.name, Toast.LENGTH_SHORT).show()
    }

    private fun showRecyclerList() {
        rvIphone.layoutManager = LinearLayoutManager(this)
        val listIphoneAdapter = ListIphoneAdapter(list)
        rvIphone.adapter = listIphoneAdapter

        listIphoneAdapter.setOnItemClickCallback(object : ListIphoneAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Iphone){
                showSelectedIphone(data)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_about -> {
                val moveIntent = Intent(this@MainActivity, AboutActivity::class.java)
                startActivity(moveIntent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.action_about -> {
                val moveIntent =Intent(this@MainActivity, AboutActivity::class.java)
                startActivity(moveIntent)
            }
        }
    }
}