package com.rushikesh.prakruti.api


data class DiseaseResponse(
    val description: String,
    val precautions: List<String>,
    val predicted_disease: String,
    val second_prediction: String
)