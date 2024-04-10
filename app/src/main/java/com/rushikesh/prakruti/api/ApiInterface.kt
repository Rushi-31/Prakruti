package com.rushikesh.prakruti.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @POST("predict")
    fun postData(@Body dataModel: DataModel?): Call<DiseaseResponse?>?
}
