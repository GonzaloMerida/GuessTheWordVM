package com.example.guesstheword.screens.game
data class GameUiState(
    val word : String = "",
    val score : Int = 0,
    val wordList : List<String> = emptyList(),
    val time : Float = 0f,
    val message : String? = null,

)