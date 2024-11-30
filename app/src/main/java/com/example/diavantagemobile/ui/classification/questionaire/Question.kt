package com.example.diavantagemobile.ui.classification.questionaire

data class Question(
    val questionText: String,
    val answers: ArrayList<String>,
    val id: Int,
    val args: QuestionArgs? = null,
)

data class QuestionArgs(
    val type: String,
    val range: ClosedFloatingPointRange<Float>? = null,
    val steps: Int? = null
)
