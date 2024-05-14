package com.example.quanlyclbbongda.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.quanlyclbbongda.data.models.Member

@Dao
interface MemberDAO {
    @Query("SELECT * FROM member")
    fun getAllMembers(): List<Member>

    @Query("SELECT * FROM member WHERE teamID = :teamID ORDER BY memberJerseyNumber ASC")
    fun findByTeamID(teamID: Int): List<Member>

    @Query("SELECT * FROM member WHERE teamID = :teamID AND memberID = :memberID LIMIT 1")
    fun findByTeamIDMemberID(teamID: Int, memberID: Int): Member

    @Query("SELECT COUNT(*) FROM member WHERE teamID = :teamID")
    fun getMemberTotalByTeamID(teamID: Int): Int

    @Query("UPDATE member SET memberName = :memberName, " +
            "memberPosition = :memberPosition, " +
            "isCaptain = :isCaptain, " +
            "memberJerseyNumber = :memberJerseyNumber, " +
            "memberPhone = :memberPhone " +
            "WHERE memberID = :memberID")
    fun updateMember(memberName: String, memberPosition: String, isCaptain: Boolean, memberJerseyNumber: Int, memberPhone: String, memberID: Int)

    @Insert
    fun insertMember(member: Member)

    @Delete
    fun deleteMember(member: Member)
}