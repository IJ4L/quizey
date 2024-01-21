package com.example.quizey

import ApiResponse
import RegistrationData
import UserData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    fun getUserData(@Query("email") email: String): Call<UserData>

    @POST("users/registrasi")
    fun registerUser(@Body registrationData: RegistrationData): Call<ApiResponse>

    @GET("exercise/data_course")
    fun getCoursesData(
        @Query("major_name") majorName: String,
        @Query("user_email") userEmail: String
    ): Call<ApiResponse>
}