package com.example.quanlyclbbongda.data.models
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.sql.Time
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "fund", foreignKeys = [
    ForeignKey(entity = Team::class, parentColumns = ["teamID"], childColumns = ["teamID"], onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)
])
data class Fund(
    @PrimaryKey(autoGenerate = true) var fundID: Int,
    @ColumnInfo var teamID: Int,
    @ColumnInfo var amountOfMoney: Int,
    @ColumnInfo var contentFund: String,
    @ColumnInfo var typeOfFund: Int,
    @ColumnInfo var time: LocalTime,
    @ColumnInfo var date: LocalDate
)
