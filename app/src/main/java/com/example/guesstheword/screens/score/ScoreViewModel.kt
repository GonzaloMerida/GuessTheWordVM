package com.example.guesstheword.screens.score

import android.util.Log
import androidx.lifecycle.ViewModel

class ScoreViewModel() : ViewModel() {
    // The final score
    private var _finalScore : Int = 0
    val finalScore get() = _finalScore

    init {
        Log.i("ScoreViewModel", "Final score is $finalScore")
    }

    fun setFinalScore(score : Int) {
        _finalScore = score
    }
}