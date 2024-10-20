package com.dicoding.dicodingeventfavoritefeatures.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.dicodingeventfavoritefeatures.R
import com.dicoding.dicodingeventfavoritefeatures.data.remote.response.DetailResponse
import com.dicoding.dicodingeventfavoritefeatures.data.remote.retrofit.ApiConfig
import com.dicoding.dicodingeventfavoritefeatures.data.remote.retrofit.ApiService
import kotlinx.coroutines.launch

class DetailEventActivity : AppCompatActivity() {

    private lateinit var imageLogo: ImageView
    private lateinit var tvEventName: TextView
    private lateinit var tvOwnerName: TextView
    private lateinit var tvBeginTime: TextView
    private lateinit var tvQuota: TextView
    private lateinit var tvDescription: TextView
    private lateinit var viewModel: EventDetailViewModel
    private lateinit var linkRegistration: String
    private lateinit var apiService: ApiService
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_event)

        supportActionBar?.title = "Detail Events"

        imageLogo = findViewById(R.id.imageLogo)
        tvEventName = findViewById(R.id.tv_event_name)
        tvOwnerName = findViewById(R.id.tv_owner_name)
        tvBeginTime = findViewById(R.id.tv_begin_time)
        tvQuota = findViewById(R.id.tv_quota)
        tvDescription = findViewById(R.id.tv_description)
        progressBar = findViewById(R.id.progress_bar)

        apiService = ApiConfig.getApiService()
        val eventId = intent.getStringExtra("event_id")
        Log.d("DetailEventActivity", "Event ID: $eventId")

        if (eventId != null){
            showloading(true)
            viewModel = ViewModelProvider(this,
                EventDetailViewModel.EventDetailViewModelFactory(apiService)
            ).get(EventDetailViewModel::class.java)

            lifecycleScope.launch {

                viewModel.getDetailEvent(eventId).observe(this@DetailEventActivity, Observer<DetailResponse> { response ->
                        if (response != null) {
                            showloading(false)
                            val event = response.event
                            tvEventName.text = event.name
                            tvOwnerName.text = event.ownerName
                            tvBeginTime.text = event.beginTime
                            linkRegistration = event.link
                            tvQuota.text = "${event.quota - event.registrants} tersisa"
                            tvDescription.text = HtmlCompat.fromHtml(
                                event.description,
                                HtmlCompat.FROM_HTML_MODE_LEGACY
                            )

                            Glide.with(this@DetailEventActivity)
                                .load(event.mediaCover)
                                .apply(RequestOptions().centerInside())
                                .into(imageLogo)

                            val btnRegister = findViewById<Button>(R.id.btn_register)
                            btnRegister.setOnClickListener{
                                val url = linkRegistration
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                startActivity(intent)

                            }
                        }
                    })
            }
        } else{
            Toast.makeText(this, "Event ID is NULL", Toast.LENGTH_SHORT).show()
        }

    }

    private fun showloading(isLoading: Boolean){
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


}