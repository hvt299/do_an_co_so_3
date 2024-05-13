package com.example.quanlyclbbongda.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.quanlyclbbongda.data.converters.DataTypeConverter
import java.time.LocalDate
import java.time.LocalTime

@TypeConverters(DataTypeConverter::class)
@Entity(tableName = "schedule", foreignKeys = [
    ForeignKey(entity = Team::class, parentColumns = ["teamID"], childColumns = ["teamID"], onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)
])
data class Schedule (
    @PrimaryKey(autoGenerate = true)
    var scheduleID: Int,
    @ColumnInfo
    var teamID: Int,
    @ColumnInfo
    var scheduleTime: LocalTime,
    @ColumnInfo
    var scheduleDate: LocalDate,
    @ColumnInfo
    var firstTeamName: String,
    @ColumnInfo
    var secondTeamName: String,
    @ColumnInfo
    var scoreMatch: String,
    @ColumnInfo
    var teamResultMatch: Int
)