package by.ssrlab.fishpits.utils.retrofit.common

import by.ssrlab.fishpits.utils.retrofit.RetrofitClient
import by.ssrlab.fishpits.utils.retrofit.`interface`.RetrofitServices

object Common {

    private const val BASE_URL = "http://fish-pits.krokam.by/api/rest/"
    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}