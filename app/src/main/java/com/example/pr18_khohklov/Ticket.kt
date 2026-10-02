package com.example.pr18_khokhlov


data class Ticket(
    var id: Long,
    var number: Int,
    var question: String,
    var answers: List<String>,
    var correctAnswer: Int,
    var isPassed: Boolean
)
