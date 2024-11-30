package com.example.diavantagemobile.util.data.interfaces

interface ClassificationStorage {
    fun updateAnswer(key: Int, value: String)

    fun getAnswers(): Map<Int, String>
}