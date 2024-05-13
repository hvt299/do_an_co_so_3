package com.example.quanlyclbbongda.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.quanlyclbbongda.data.models.Team

@Dao
interface TeamDAO {
    @Query("SELECT * FROM team")
    fun getAllTeams(): List<Team>

    @Query("SELECT * FROM team WHERE userEmail LIKE :email LIMIT 1")
    fun findByEmail(email: String): Team

    @Query("SELECT * FROM team WHERE teamID = :teamID")
    fun findByTeamID(teamID: Int): Team

    @Query("UPDATE team SET teamName = :teamName, teamPitchSize = :teamPitchSize, teamAddress = :teamAddress WHERE teamID = :teamID AND userEmail = :userEmail")
    fun updateTeam(teamName: String, teamPitchSize: String, teamAddress: String, teamID: Int, userEmail: String)

    @Insert
    fun insertTeam(team: Team)

    @Delete
    fun deleteTeam(team: Team)
}