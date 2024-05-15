package com.example.quanlyclbbongda.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.quanlyclbbongda.data.models.Fund

@Dao
interface FundDAO {
   @Query("SELECT * FROM fund WHERE fundID = :fundID limit 1")
   fun getFundByFundID(fundID: Int): Fund

   @Query("SELECT COUNT(*) FROM fund WHERE teamID = :teamID")
   fun getNumberFundByTeamID(teamID: Int): Int

   @Query("SELECT * FROM fund WHERE teamID = :teamID")
   fun getFundByTeamID(teamID: Int): List<Fund>



   @Insert
   fun insertFund(fund: Fund)

   @Delete
   fun deleteFund(fund: Fund)
}