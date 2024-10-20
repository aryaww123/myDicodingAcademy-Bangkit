package com.dicoding.dicodingeventfavoritefeatures.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.dicodingeventfavoritefeatures.R
import com.dicoding.dicodingeventfavoritefeatures.ui.detail.DetailEventActivity

data class EventItem(
    val id: Int,
    val name: String,
    val mediaCover: String
)

class EventAdapter(private val context: Context) : ListAdapter<EventItem, EventAdapter.EventViewHolder>(DIFF_CALLBACK){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventViewHolder {

        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)

    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = getItem(position)

        holder.tv_event_name.text = event.name

        Glide.with(context)
            .load(event.mediaCover)
            .apply(RequestOptions().centerInside())
            .into(holder.imageLogo)

        holder.itemView.setOnClickListener{
            val eventId = event.id.toString()
            val intent = Intent(context, DetailEventActivity::class.java)
            intent.putExtra("event_id", eventId)
            context.startActivity(intent)
        }
    }

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageLogo: ImageView = itemView.findViewById(R.id.imageLogo)
        val tv_event_name: TextView = itemView.findViewById(R.id.tv_event_name)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EventItem>(){
            override fun areItemsTheSame (
                oldItem: EventItem,
                newItem: EventItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: EventItem,
                newItem: EventItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}