package com.dicoding.dicodingeventfavoritefeatures.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
class EventEntity (
    @field:ColumnInfo(name = "eventName")
    @field:PrimaryKey(autoGenerate = false)
    val eventName: String,

    @field:ColumnInfo(name = "eventId")
    var eventId: String,

    @field:ColumnInfo(name = "ownerName")
    val ownerName: String,

    @field:ColumnInfo(name = "urlToImage")
    val urlToImage: String? = null,

    @field:ColumnInfo(name = "url")
    val url: String? = null,

    @field:ColumnInfo(name = "favorite")
    var favorite: Boolean
)