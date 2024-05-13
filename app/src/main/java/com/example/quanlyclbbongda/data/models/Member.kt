package com.example.quanlyclbbongda.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "member", foreignKeys = [
    ForeignKey(entity = Team::class, parentColumns = ["teamID"], childColumns = ["teamID"], onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)
])
data class Member(
    @PrimaryKey(autoGenerate = true) var memberID: Int,
    @ColumnInfo var teamID: Int,
    @ColumnInfo var memberName: String,
    @ColumnInfo var memberPosition: String,
    @ColumnInfo var isCaptain: Boolean,
    @ColumnInfo var memberJerseyNumber: Int,
    @ColumnInfo var memberPhone: String
)