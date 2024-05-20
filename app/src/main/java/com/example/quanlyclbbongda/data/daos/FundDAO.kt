package com.example.quanlyclbbongda.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.quanlyclbbongda.data.models.Fund
import java.sql.Date
import java.sql.Time
import java.time.LocalDate
import java.time.LocalTime

@Dao
interface FundDAO {
   @Query("SELECT * FROM fund WHERE fundID = :fundID limit 1")
   fun getFundByFundID(fundID: Int): Fund

   @Query("SELECT COUNT(*) FROM fund WHERE teamID = :teamID")
   fun getNumberFundByTeamID(teamID: Int): Int

   @Query("SELECT * FROM fund WHERE teamID = :teamID")
   fun getFundByTeamID(teamID: Int): List<Fund>

   @Query("SELECT * FROM fund WHERE teamID = :teamID and fundID = :fundID")
   fun findByTeamIDFundID(teamID: Int, fundID: Int): Fund
   // select Khoản thu
   @Query("SELECT SUM(amountOfMoney) FROM fund WHERE teamID = :teamID and typeOfFund = :typeOfFund")
   fun getAmountOfMoneyByTeamIDByTypeOfFund(teamID: Int, typeOfFund: Int): Int

   @Insert
   fun insertFund(fund: Fund)

   @Delete
   fun deleteFund(fund: Fund)
   @Query("UPDATE fund SET amountOfMoney = :amountOfMoney, " +
           "contentFund = :contenFund, " +
           "typeOfFund = :typeOfFund, " +
           "time = :time, " +
           "date = :date " +
            "WHERE fundId = :fundID and teamID = :teamID")


   fun updateFund(amountOfMoney: Int, contenFund: String, typeOfFund: Int, time: LocalTime, date: LocalDate, fundID: Int, teamID: Int)
}