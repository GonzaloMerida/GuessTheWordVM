package com.example.guesstheword.screens.game

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.guesstheword.app.MyApp
import com.example.guesstheword.datasource.MyTimer
import com.example.guesstheword.repositories.WordsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.floor

class GameViewModel(val wordsRepository: WordsRepository) : ViewModel() {

    //inicialización de un flujo de estado mutable de los elementos de la interfaz
    private val _gameUIState: MutableStateFlow<GameUiState> = MutableStateFlow(GameUiState())
    val gameUiState: StateFlow<GameUiState>
        get() = _gameUIState.asStateFlow()


    private lateinit var _myTimer : Flow<Float>
    val myTimer get() = _myTimer

    private val _correctWords: MutableList<String> = mutableListOf()
    private val _wrongWords: MutableList<String> = mutableListOf()

    init {
        Log.i("GameViewModel", "GameViewModel creado!")
        wordsRepository.initList()
        _gameUIState.update { currentSate ->
            currentSate.copy(
                word = wordsRepository.wordList.get(0),
                score = 0,
                wordList = wordsRepository.wordList.subList(1, wordsRepository.wordList.size)
            )
        }

        //lanzo corrutina con un temporizador
        viewModelScope.launch {
            MyTimer(10f, 0.5f, 500).timer.filter {
                it - floor(it) == 0f
            }.collect {
                _gameUIState.update { currentState ->
                    currentState.copy(
                        time = it
                    )
                }
            }
        }

        _myTimer = MyTimer(10f,0.5f, 500).timer.filter {
            Log.i("GameViewModel",it.toString())
            it - floor(it) == 0f
        }.catch { error ->
            Log.i("GameViewModel", "Ha ocurrido un error")
            emit(0f)
        }

//        //con filter, envía los datos que cumplan la condición que le indicamos (que sean cada segundo)
//        //que envíe hacia arriba sólo los it que cumplan que pase cada segundo y no cada 0.5
//        _myTimer = MyTimer(10f, 0.5f, 500).timer.filter {
//            Log.i("GameViewModel", it.toString())
//            //it, es el propio float del productor
//            //floor devuelve el valor entero más próximo por debajo del nº
//            //05 - 0 = 0.5 == 0? no, pues este no se pasa hacia arriba
//            //1 - 1 = 0 == 0? sí, pues este sí pasa el filtro
//            it - floor(it) == 0f
//        }.catch { error ->
//            Log.i("GameViewModel", "Ha ocurrido un error")
//            emit(0f)
//        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destruido!")
    }

    //Función que toma una nueva palabra e incrementa o decrementa la puntuación.
    fun nextWord(acierto: Boolean) {
        //val inc = if (acierto) 1 else -1
        //if (gameUiState.value.wordList.isNotEmpty()) {
        //    _gameUIState.update { currentState ->
        //        currentState.copy(
        //            word = currentState.wordList.get(0),
        //            score = currentState.score + inc,
        //            wordList = currentState.wordList.subList(1, currentState.wordList.size)
        //        )
        //    }
        //} else {
        //    _gameUIState.update { currenState ->
        //        currenState.copy(
        //            word = "",
        //            score = currenState.score + inc,
        //            wordList = emptyList()
        //        )
        //    }
        //}
        //if (gameUiState.value.score == 10 && inc == 1)
        //    _gameUIState.update { currenState ->
        //        currenState.copy(
        //            message = "Good score!"
        //        )
        //    }
        val inc = if (acierto) 1 else -1
        val currentWord = gameUiState.value.word
        if (acierto) {
            _correctWords.add(currentWord)
        } else {
            _wrongWords.add(currentWord)
        }
        if (gameUiState.value.wordList.isNotEmpty()) {
            _gameUIState.update { currentState ->
                currentState.copy(
                    word = currentState.wordList[0],
                    score = currentState.score + inc,
                    wordList = currentState.wordList.subList(1, currentState.wordList.size)
                )
            }
        } else {
            _gameUIState.update { currenState ->
                currenState.copy(
                    word = "",
                    score = currenState.score + inc,
                    wordList = emptyList()
                )
            }
        }
        if (gameUiState.value.score == 10 && inc == 1)
            _gameUIState.update { currenState ->
                currenState.copy(
                    message = "Good score!"
                )
            }
    }

    fun messageShown() {
        _gameUIState.update { currenState ->
            currenState.copy(
                message = null
            )
        }
    }

    fun getCorrectWords(): MutableList<String> {
        return _correctWords.toMutableList()
    }

    fun getWrongWords(): MutableList<String> {
        return _wrongWords.toMutableList()
    }

//    fun onSkip() {
//        _score--
//        nextWord()
//    }
//
//    fun onCorrect() {
//        _score++
//        nextWord()
//    }
//
//    fun nextWord() {
//        if (!wordList.isEmpty()) {
//            //Select and remove a word from the list
//            _word = wordList.removeAt(0)
//        } else {
//            _word = NO_WORD
//        }
//    }

    companion object {

        const val NO_WORD = "no_word"

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])

                return GameViewModel(
                    (application as MyApp).appContainer.wordsRepository,
                ) as T
            }
        }
    }
}