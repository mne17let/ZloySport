package com.zloysport.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Drill(
    @PrimaryKey val id: Int,
    val name: String,
)