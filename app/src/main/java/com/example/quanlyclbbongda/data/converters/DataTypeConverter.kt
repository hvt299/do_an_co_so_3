package com.example.quanlyclbbongda.data.converters

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalTime

@SuppressLint("NewApi")
class DataTypeConverter {
    @TypeConverter
    fun toDate(dateString: String): LocalDate? {
        if (dateString == null) return null
        else {
            return LocalDate.parse(dateString)
        }
    }

    @TypeConverter
    fun fromDate(date: LocalDate): String? {
        var dateString = date.toString()
        if (dateString == null) return null
        else {
           return dateString
        }
    }

    @TypeConverter
    fun toTime(timeString: String): LocalTime? {
        if (timeString == null) return null
        else {
            return LocalTime.parse(timeString)
        }
    }

    @TypeConverter
    fun fromTime(time: LocalTime): String? {
        var timeString = time.toString()
        if (timeString == null) return null
        else {
            return timeString
        }
    }
}