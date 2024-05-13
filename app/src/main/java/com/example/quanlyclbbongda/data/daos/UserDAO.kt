package com.example.quanlyclbbongda.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.quanlyclbbongda.data.models.User

@Dao
interface UserDAO {
    @Query("SELECT * FROM user")
    fun getAllUsers(): List<User>

    @Query("SELECT * FROM user WHERE email LIKE :email AND password LIKE :password LIMIT 1")
    fun findByEmailPassword(email: String, password: String): User

    @Insert
    fun insertUser(user: User)

    @Delete
    fun deleteUser(user: User)
}