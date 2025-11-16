package com.example.orm.data

import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM Utilisateurs")
    fun getAllUsers(): List<User>

    @Insert
    fun insertUser(user: User)

    @Delete
    fun deleteUser(user: User)
}