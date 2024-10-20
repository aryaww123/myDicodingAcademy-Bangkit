package com.dicoding.dicodingeventfavoritefeatures.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.*
import com.dicoding.dicodingeventfavoritefeatures.data.local.entity.EventEntity


@Dao
interface EventDao {
    @Query("SELECT * FROM events ORDER BY ownerName DESC")
    fun getEvent(): LiveData<List<EventEntity>>

    @Query("SELECT * FROM events where favorite = 1")
    fun getFavoriteEvent(): LiveData<List<EventEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertEvent(events: List<EventEntity>)

    @Update
    fun updateEvent(events: EventEntity)

    @Query("DELETE FROM events WHERE favorite = 0")
    fun deleteAll()

    @Query("SELECT EXISTS (SELECT * FROM events WHERE eventName = :eventName AND favorite = 1)")
    fun isEventFavorite(eventName: String): Boolean
}