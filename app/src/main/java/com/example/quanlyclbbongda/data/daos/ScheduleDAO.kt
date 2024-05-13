package com.example.quanlyclbbongda.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.quanlyclbbongda.data.models.Member
import com.example.quanlyclbbongda.data.models.Schedule
import java.time.LocalDate
import java.time.LocalTime

@Dao
interface ScheduleDAO {
    @Query("SELECT * FROM schedule")
    fun getALlSchedules(): List<Schedule>

    @Query("SELECT * FROM schedule WHERE teamID = :teamID")
    fun findByTeamID(teamID: Int): List<Schedule>

    @Query("SELECT * FROM schedule WHERE teamID = :teamID AND scheduleID = :scheduleID LIMIT 1")
    fun findByTeamIDScheduleID(teamID: Int, scheduleID: Int): Schedule

    @Query("UPDATE schedule SET scheduleTime = :scheduleTime, " +
            "scheduleDate = :scheduleDate, " +
            "firstTeamName = :firstTeamName, " +
            "secondTeamName = :secondTeamName, " +
            "scoreMatch = :scoreMatch, " +
            "teamResultMatch = :teamResultMatch " +
            "WHERE scheduleID = :scheduleID")
    fun updateSchedule(scheduleTime: LocalTime, scheduleDate: LocalDate, firstTeamName: String, secondTeamName: String, scoreMatch: String, teamResultMatch: Int, scheduleID: Int)

    @Insert
    fun insertSchedule(schedule: Schedule)

    @Delete
    fun deleteSchedule(schedule: Schedule)
}