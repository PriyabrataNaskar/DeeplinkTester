package com.priyo.deeplinktesterpro.home.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "deeplinks")
data class DeepLink(
    val deepLink: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)