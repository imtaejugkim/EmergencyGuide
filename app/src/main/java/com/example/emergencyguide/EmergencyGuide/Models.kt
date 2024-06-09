package com.example.emergencyguide.EmergencyGuide

data class StepData(
    val imageResId: Int,
    val title: String,
    val description: String
)

data class ExpandableBoxData(
    val title: String,
    val content: String,
    val youtubeUrl: String,
    val steps: List<StepData>
)
