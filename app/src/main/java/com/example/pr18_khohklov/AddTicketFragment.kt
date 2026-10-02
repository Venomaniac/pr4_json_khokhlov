package com.example.pr18_khokhlov

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment


class AddTicketFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_add, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val etNumber = view.findViewById<EditText>(R.id.etNumber)
        val etQuestion = view.findViewById<EditText>(R.id.etQuestion)
        val etAnswers = listOf(
            view.findViewById<EditText>(R.id.etAnswer1),
            view.findViewById<EditText>(R.id.etAnswer2),
            view.findViewById<EditText>(R.id.etAnswer3),
            view.findViewById<EditText>(R.id.etAnswer4)
        )
        val spCorrect = view.findViewById<Spinner>(R.id.spCorrect)
        val cbPassed = view.findViewById<CheckBox>(R.id.cbPassed)
        val btnSave = view.findViewById<Button>(R.id.btnSave)

        // Spinner с номерами ответов (1-4)
        ArrayAdapter.createFromResource(
            requireContext(), R.array.answer_numbers, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spCorrect.adapter = adapter
        }

        btnSave.setOnClickListener {
            val numberText = etNumber.text.toString()
            val question = etQuestion.text.toString()
            val answers = etAnswers.map { it.text.toString() }


            if (numberText.isEmpty()) { etNumber.error = "Введите номер билета"; return@setOnClickListener }
            if (question.isEmpty()) { etQuestion.error = "Введите вопрос"; return@setOnClickListener }
            for (i in answers.indices) {
                if (answers[i].isEmpty()) {
                    etAnswers[i].error = "Введите вариант ответа"
                    return@setOnClickListener
                }
            }

            val ticket = Ticket(
                id = System.currentTimeMillis(),
                number = numberText.toInt(),
                question = question,
                answers = answers,
                correctAnswer = spCorrect.selectedItemPosition + 1,
                isPassed = cbPassed.isChecked
            )
            Storage.addTicket(requireContext(), ticket)
            Toast.makeText(requireContext(), "Билет сохранён", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        }
    }
}
