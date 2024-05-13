package com.example.quanlyclbbongda.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey var email: String,
    @ColumnInfo var password: String
)
