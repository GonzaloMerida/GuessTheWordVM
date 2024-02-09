package com.example.guesstheword.datamodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Words")
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0L,
    @ColumnInfo("title")
    val word : String = ""
)

