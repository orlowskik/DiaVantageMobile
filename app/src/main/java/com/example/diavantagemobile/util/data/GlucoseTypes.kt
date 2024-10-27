package com.example.diavantagemobile.util.data

data class GlucoseTypes(
    val map: Map<Int, String> = mapOf(
        0 to "undefined",
        1 to "Before Breakfast",
        2 to "After Breakfast",
        3 to "Before Lunch",
        4 to "After Lunch",
        5 to "Before Dinner",
        6 to "After Dinner"
    )
)
