package com.homecontentlock.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "config")
data class ConfigEntity(
    @PrimaryKey
    val key: String,
    val value: String
)