package com.example.quanlyclbbongda.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "team", foreignKeys = [
    ForeignKey(entity = User::class, parentColumns = ["email"], childColumns = ["userEmail"], onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)
])
data class Team(
    @PrimaryKey(autoGenerate = true) var teamID: Int,
    @ColumnInfo var userEmail: String,
    @ColumnInfo var teamName: String,
    @ColumnInfo var teamPitchSize: String,
    @ColumnInfo var teamAddress: String
)
