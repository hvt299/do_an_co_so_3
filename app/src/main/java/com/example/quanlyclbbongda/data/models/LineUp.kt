package com.example.quanlyclbbongda.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "lineup", foreignKeys = [
    ForeignKey(entity = Schedule::class, parentColumns = ["scheduleID"], childColumns = ["scheduleID"], onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE),
    ForeignKey(entity = Member::class, parentColumns = ["memberID"], childColumns = ["memberID"], onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)
])

data class LineUp(
    @PrimaryKey var scheduleID: Int,
    @PrimaryKey var memberID: Int
)
