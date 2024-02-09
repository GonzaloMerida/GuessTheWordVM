package com.example.guesstheword.screens.score

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.guesstheword.app.MyApp
import com.example.guesstheword.screens.game.GameViewModel

class ScoreViewModel() : ViewModel() {
    // The final score
    private var _finalScore : Int =0
    val finalScore get() = _finalScore

    private var _wrongWords : String = ""
        val wrongWords
        get() = _wrongWords

    private var _correctWords : String = ""
    val correctWords
        get() = _correctWords

    init {
        Log.i("ScoreViewModel", "Final score is $finalScore")
    }

    fun setFinalScore(score : Int) {
        _finalScore = score
    }

    fun setCorrectWords(words : String){
        _correctWords = words
    }

    fun setWrongWords(words : String){
        _wrongWords = words
    }
}