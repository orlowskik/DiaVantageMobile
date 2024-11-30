package com.example.diavantagemobile.util.data.storages

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.diavantagemobile.util.data.interfaces.ClassificationStorage

class RealClassificationStorage: ClassificationStorage {
    private var answers = mutableStateMapOf<Int, String>()

    override fun updateAnswer(key: Int, value: String) {
        answers[key] = value
        Log.i("Class Storage", answers.toString())
    }

    override fun getAnswers(): Map<Int, String> {
        return answers
    }

}