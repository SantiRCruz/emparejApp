package com.example.emparejapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlayerEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val name : String = "",
    val points : Int = 0,
    val level : Int,
    val time : String
)
