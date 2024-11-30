package com.example.diavantagemobile.util

import com.example.diavantagemobile.util.data.interfaces.ClassificationStorage

class ClassificationInfoProvider (
    private val classificationStorage: ClassificationStorage,
){
    fun getDistinctInfo(key: Int): String?{
        return classificationStorage.getAnswers()[key]
    }

    fun getInfo(): Map<Int, String>{
        return classificationStorage.getAnswers()
    }

    fun updateUserInfo(key: Int, value: String){
        classificationStorage.updateAnswer(key, value)
    }
}