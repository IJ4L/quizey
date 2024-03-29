package com.example.quizey.model

data class ApiResponse(
    val status: Int,
    val message: String,
    val data: List<Course> // Assuming data is a list of Course objects
)

data class Course(
    val course_id: String,
    val major_name: String,
    val course_category: String,
    val course_name: String,
    val url_cover: String,
    val jumlah_materi: Int,
    val jumlah_done: Int,
    val progress: Int
)

data class RecyclerDataMapel(
    val courseName: String,
    val courseId: Int
)


data class ExerciseResponse(
    val status: Int,
    val message: String,
    val data: List<ExerciseItem>
)

data class ExerciseItem(
    val exercise_id: String,
    val exercise_title: String,
    val access_type: String,
    val icon: String,
    val exercise_user_status: String,
    val jumlah_soal: String,
    val jumlah_done: Int
)
