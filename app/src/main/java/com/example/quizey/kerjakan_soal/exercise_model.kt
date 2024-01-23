import com.google.gson.annotations.SerializedName

data class ExerciseResponseData(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<ExerciseItemData>
)

data class ExerciseItemData(
    @SerializedName("exercise_id_fk") val exerciseIdFk: String,
    @SerializedName("bank_question_id") val bankQuestionId: String,
    @SerializedName("question_title") val questionTitle: String,
    @SerializedName("question_title_img") val questionTitleImg: String?,
    @SerializedName("option_a") val optionA: String,
    @SerializedName("option_a_img") val optionAImg: String?,
    @SerializedName("option_b") val optionB: String,
    @SerializedName("option_b_img") val optionBImg: String?,
    @SerializedName("option_c") val optionC: String,
    @SerializedName("option_c_img") val optionCImg: String?,
    @SerializedName("option_d") val optionD: String,
    @SerializedName("option_d_img") val optionDImg: String?,
    @SerializedName("option_e") val optionE: String,
    @SerializedName("option_e_img") val optionEImg: String?,
    @SerializedName("student_answer") val studentAnswer: String
)

data class ExerciseModelDone(
    val status: Int,
    val message: String,
    val data: ExerciseData
)

data class ExerciseData(
    val exercise: Exercise,
    val result: Result
)

data class Exercise(
    @SerializedName("exercise_id")
    val exerciseId: String,
    @SerializedName("exercise_code")
    val exerciseCode: String,
    @SerializedName("file_course")
    val fileCourse: String,
    val icon: String,
    @SerializedName("exercise_title")
    val exerciseTitle: String,
    @SerializedName("exercise_description")
    val exerciseDescription: String,
    @SerializedName("exercise_instruction")
    val exerciseInstruction: String,
    @SerializedName("count_question")
    val countQuestion: String,
    @SerializedName("class_fk")
    val classFk: String,
    @SerializedName("course_fk")
    val courseFk: String,
    @SerializedName("course_content_fk")
    val courseContentFk: String,
    @SerializedName("sub_course_content_fk")
    val subCourseContentFk: String,
    @SerializedName("creator_id")
    val creatorId: String,
    @SerializedName("creator_name")
    val creatorName: String,
    @SerializedName("exam_from")
    val examFrom: String,
    @SerializedName("access_type")
    val accessType: String,
    @SerializedName("exercise_order")
    val exerciseOrder: String,
    @SerializedName("exercise_status")
    val exerciseStatus: String,
    @SerializedName("date_create")
    val dateCreate: String,
    @SerializedName("date_update")
    val dateUpdate: String
)

data class Result(
    @SerializedName("jumlah_benar")
    val jumlahBenar: Int,
    @SerializedName("jumlah_salah")
    val jumlahSalah: String,
    @SerializedName("jumlah_tidak")
    val jumlahTidak: String,
    @SerializedName("jumlah_score")
    val jumlahScore: String
)
