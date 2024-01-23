package com.example.quizey.api_service

import ApiResponse
import ExerciseModelDone
import ExerciseResponseData
import RegistrationData
import UserData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
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
    ): Call<com.example.quizey.model.ApiResponse>

    @GET("exercise/data_exercise")
    fun getExerciseData(
        @Query("course_id") courseid: String,
        @Query("user_email") userEmail: String
    ): Call<com.example.quizey.model.ExerciseResponse>

    @FormUrlEncoded
    @POST("exercise/kerjakan")
    fun getExerciseDataSoal(
        @Field("exercise_id") exerciseId: String,
        @Field("user_email") userEmail: String
    ): Call<ExerciseResponseData>

    @FormUrlEncoded
    @POST("exercise/input_jawaban")
    suspend fun postExerciseAnswers(
        @Field("user_email") userEmail: String,
        @Field("exercise_id") exerciseId: String,
        @Field("question_ids[]") questionIds: List<String>,
        @Field("student_answers[]") studentAnswers: List<String>
    )

    @FormUrlEncoded
    @POST("exercise/donetest")
    suspend fun postDoneExerciseAnswers(
        @Field("user_email") userEmail: String,
        @Field("exercise_id") exerciseId: String,
    )

    @GET("exercise/score_result")
    fun getValueTest(
        @Query("exercise_id") exerciseId: String,
        @Query("user_email") userEmail: String
    ): Call<ExerciseModelDone>
}