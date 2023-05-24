package com.pechenegmobilecompanyltd.sportsquiz.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.pechenegmobilecompanyltd.sportsquiz.R
import com.pechenegmobilecompanyltd.sportsquiz.databinding.FragmentSelectDifficultyBinding

var difficulty = ""

class SelectDifficultyFragment : Fragment() {

    private var _binding: FragmentSelectDifficultyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectDifficultyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonEasy.setOnClickListener {
            difficulty = "Easy"
            findNavController().navigate(R.id.action_SelectDifficultyFragment_to_QuizFragment)
        }

        binding.buttonMedium.setOnClickListener {
            difficulty = "Medium"
            findNavController().navigate(R.id.action_SelectDifficultyFragment_to_QuizFragment)
        }

        binding.buttonHard.setOnClickListener {
            difficulty = "Hard"
            findNavController().navigate(R.id.action_SelectDifficultyFragment_to_QuizFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}