package com.example.emparejapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.ABORT
import androidx.room.Query
import com.example.emparejapp.data.models.PlayerEntity

@Dao
interface PlayerDao {
    @Insert(onConflict = ABORT)
    suspend fun savePlayer(playerEntity: PlayerEntity):Long

    @Query("SELECT * FROM playerentity WHERE level = :level ORDER BY points DESC LIMIT 5 ")
    suspend fun getFirstPlayers(level:Int):List<PlayerEntity>
}