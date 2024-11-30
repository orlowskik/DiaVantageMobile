package com.example.diavantagemobile.util

import com.example.diavantagemobile.util.data.interfaces.ClassificationStorage
import com.example.diavantagemobile.util.data.storages.RealClassificationStorage

object ClassificationInfo {
    private val classificationStorage: ClassificationStorage = RealClassificationStorage()
    val classificationInfo: ClassificationInfoProvider = ClassificationInfoProvider(
        classificationStorage)
}