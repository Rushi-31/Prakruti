package com.rushikesh.prakruti.api

import android.content.Context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


fun postData(
    context: Context,
    symptoms: MutableList<String>,
    days: Int,
    onPredictionReceived: (String, String, List<String>) -> Unit,
    onFailure: (String) -> Unit
) {
    val intDays = days
    val dataModel = DataModel(intDays, symptoms = symptoms)

    RetrofitInstance.apiInterface.postData(dataModel)?.enqueue(object : Callback<DiseaseResponse?> {
        override fun onResponse(
            call: Call<DiseaseResponse?>,
            response: Response<DiseaseResponse?>
        ) {

            val diseaseResponse: DiseaseResponse? = response.body()

            if (diseaseResponse != null) {
                onPredictionReceived(
                    diseaseResponse.predicted_disease,
                    diseaseResponse.description,
                    diseaseResponse.precautions
                )
            } else {
                onFailure("Empty response body")
            }
        }

        override fun onFailure(call: Call<DiseaseResponse?>, t: Throwable) {
            onFailure("Error found: ${t.message}")
        }
    })
}

