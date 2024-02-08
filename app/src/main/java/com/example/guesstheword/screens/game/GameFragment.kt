/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.guesstheword.screens.game

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.guesstheword.R
import com.example.guesstheword.databinding.GameFragmentBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {

    private lateinit var binding: GameFragmentBinding

    private val gameVM : GameViewModel by viewModels { GameViewModel.Factory }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate view and obtain an instance of the binding class
        binding = GameFragmentBinding.inflate(inflater,container,false)

        //initialization.
//        updateScoreText()
//        updateWordText()

        //Listeners
        setListeners()

        //Collectors
        setCollectros()
        //en un fragment todos los consumos de flujos se lanzan  con lifecycleScope
        //en un viewModel todos los consumos de flujos se lanzan con viewModelScope

//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                gameVM.myTimer.collect {
//                    binding.tvTimer.text = it.toString()
//                }
//            }
//        }

        return binding.root

    }

    private fun setListeners() {
        binding.correctButton.setOnClickListener { gameVM.nextWord(true) }
        binding.skipButton.setOnClickListener { gameVM.nextWord(false) }
        binding.endGameButton.setOnClickListener { onEndGame() }
    }

    private fun setCollectros() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                gameVM.gameUiState.collect { gameState ->
                    binding.scoreText.text = gameState.score.toString()
                    binding.wordText.text = gameState.word
                    binding.tvTimer.text = gameState.time.toInt().toString()
                    if(gameState.word=="" && gameState.wordList.isEmpty()) {
                        binding.correctButton.isEnabled = false
                        binding.skipButton.isEnabled = false
                        binding.wordText.text = getString(R.string.no_word)
                    }
                    gameState.message?.let{
                        Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
                        gameVM.messageShown()
                    }
                }
            }
        }
    }

    private fun onEndGame() {
        Snackbar.make(requireView(), getString(R.string.no_word), Snackbar.LENGTH_SHORT).show()
        //cómo paso los 3 valores como args?
        val correctWords = gameVM.getCorrectWords()
        val wrongWords = gameVM.getWrongWords()
        //val action = GameFragmentDirections.actionGameFragmentToScoreFragment()
        //action.finalScore = gameVM.gameUiState.value.score
        //findNavController().navigate(action)
    }

//    fun onSkip() {
//        gameVM.onSkip()
//        updateWordText()
//        updateScoreText()
//    }
//
//    fun onCorrect() {
//        gameVM.onCorrect()
//        updateWordText()
//        updateScoreText()
//    }
//
//
//    /** Methods for updating the UI **/
//
//    private fun updateWordText() {
//        binding.wordText.text = gameVM.word
//        if(gameVM.word == GameViewModel.NO_WORD) {
//            binding.wordText.text = getString(R.string.no_word)
//            binding.correctButton.isEnabled = false
//            binding.skipButton.isEnabled = false
//        }
//    }
//
//    private fun updateScoreText() {
//        binding.scoreText.text = gameVM.score.toString()
//    }
}
