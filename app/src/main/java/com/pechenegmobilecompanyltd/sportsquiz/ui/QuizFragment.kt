package com.pechenegmobilecompanyltd.sportsquiz.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.pechenegmobilecompanyltd.sportsquiz.R
import com.pechenegmobilecompanyltd.sportsquiz.databinding.FragmentQuizBinding
import com.pechenegmobilecompanyltd.sportsquiz.entity.Question
import kotlinx.coroutines.launch

var countTrueAnswer = 0

class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModel by viewModels()
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        countTrueAnswer = 0
        var balance = getPointCount()
        var countQuestion = 0

        val textDifficult = String.format(resources.getString(R.string.difficult, difficulty))
        binding.textDifficulty.text = textDifficult

        var textCountQuestion = String.format(
            resources.getString(
                R.string.count_question,
                (countQuestion + 1).toString()
            )
        )
        binding.textCountQuestion.text = textCountQuestion

        viewLifecycleOwner.lifecycleScope.launch {
            val questions = mutableListOf<Question>()
            val listQuestions =
                viewModel.loadQuestionsList("api.php/?amount=11&difficulty=${difficulty.lowercase()}&type=multiple&category=21")
            listQuestions.forEach {
                questions.add(it)
            }

            bindingQuestion(questions, countQuestion)

            binding.firstAnswer.setOnClickListener {
                countQuestion++
                if (countQuestion == 10) {
                    balance += countTrueAnswer
                    savePointsPrefData(balance)
                    findNavController().navigate(R.id.action_QuizFragment_to_ResultFragment)
                } else {
                    if (questions[countQuestion - 1].correct_answer == binding.firstAnswer.text) {
                        countTrueAnswer++
                        bindingQuestion(questions, countQuestion)
                    } else bindingQuestion(questions, countQuestion)
                }
                textCountQuestion = String.format(
                    resources.getString(
                        R.string.count_question,
                        (countQuestion + 1).toString()
                    )
                )
                binding.textCountQuestion.text = textCountQuestion
            }

            binding.secondAnswer.setOnClickListener {
                countQuestion++
                if (countQuestion == 10) {
                    balance += countTrueAnswer
                    savePointsPrefData(balance)
                    findNavController().navigate(R.id.action_QuizFragment_to_ResultFragment)
                } else {
                    if (questions[countQuestion - 1].correct_answer == binding.secondAnswer.text) {
                        countTrueAnswer++
                        bindingQuestion(questions, countQuestion)
                    } else bindingQuestion(questions, countQuestion)
                }
                textCountQuestion = String.format(
                    resources.getString(
                        R.string.count_question,
                        (countQuestion + 1).toString()
                    )
                )
                binding.textCountQuestion.text = textCountQuestion
            }

            binding.thirdAnswer.setOnClickListener {
                countQuestion++
                if (countQuestion == 10) {
                    balance += countTrueAnswer
                    savePointsPrefData(balance)
                    findNavController().navigate(R.id.action_QuizFragment_to_ResultFragment)
                } else {
                    if (questions[countQuestion - 1].correct_answer == binding.thirdAnswer.text) {
                        countTrueAnswer++
                        bindingQuestion(questions, countQuestion)
                    } else bindingQuestion(questions, countQuestion)
                }
                textCountQuestion = String.format(
                    resources.getString(
                        R.string.count_question,
                        (countQuestion + 1).toString()
                    )
                )
                binding.textCountQuestion.text = textCountQuestion
            }
        }
    }

    private fun bindingQuestion(questions: List<Question>, countQuestion: Int) {
        val listButtons =
            listOf(binding.firstAnswer, binding.secondAnswer, binding.thirdAnswer).shuffled()
        binding.textQuestion.text = decodeText(questions[countQuestion].question)
        listButtons[0].text = decodeText(questions[countQuestion].correct_answer)
        listButtons[1].text =
            decodeText(questions[countQuestion].incorrect_answers[0])
        listButtons[2].text =
            decodeText(questions[countQuestion].incorrect_answers[1])
    }

    private fun decodeText(text: String): String {
        return if (Build.VERSION.SDK_INT >= 24) {
            Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY).toString()
        } else {
            Html.fromHtml(text).toString()
        }
    }

    private fun getPointCount(): Int {
        sharedPreferences = context?.getSharedPreferences("point", Context.MODE_PRIVATE)
        return sharedPreferences!!.getInt("points", 0)
    }

    private fun savePointsPrefData(points: Int) {
        sharedPreferences = context?.getSharedPreferences("point", Context.MODE_PRIVATE)
        val editor = sharedPreferences!!.edit()
        editor.putInt("points", points)
        editor.apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}