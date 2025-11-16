package com.example.orm.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Utilisateurs")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nom: String,
    val email: String
)