package com.example.quanlyclbbongda.data.daos

import androidx.room.Query
import com.example.quanlyclbbongda.data.models.LineUp

interface lineupDAO {
    @Query("SELECT * FROM lineup WHERE scheduleID = :scheduleID and memberID = :memberID")
    fun getLineUp(scheduleID: Int, memberID: Int) : List<LineUp>
}