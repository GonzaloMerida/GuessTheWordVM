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

package com.example.guesstheword.screens.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.guesstheword.databinding.ScoreFragmentBinding
import com.example.guesstheword.screens.game.GameFragmentDirections

/**
 * Fragment where the final score is shown, after the game is over
 */
class ScoreFragment : Fragment() {

    private lateinit var binding : ScoreFragmentBinding

    private val args : ScoreFragmentArgs by navArgs()

    private val scoreVM : ScoreViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate view and obtain an instance of the binding class.
        binding = ScoreFragmentBinding.inflate(inflater,container,false)

        binding.playAgainButton.setOnClickListener{
            val action = ScoreFragmentDirections.actionScoreFragmentToGameFragment()
            findNavController().navigate(action)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Inicializa el ViewModel
        scoreVM.setFinalScore(args.finalScore)
        scoreVM.setCorrectWords(args.correctWords)
        scoreVM.setWrongWords(args.wrongWords)

        binding.scoreText.text = scoreVM.finalScore.toString()
        binding.correctWordsList.text = scoreVM.correctWords
        binding.wrongWordsList.text = scoreVM.wrongWords

    }
}
