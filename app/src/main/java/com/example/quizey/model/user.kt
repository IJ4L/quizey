import com.google.gson.annotations.SerializedName

data class ApiResponse(
    val status: Int,
    val message: String,
    val data: UserData
)

data class UserData(
    @SerializedName("iduser") val userId: String,
    @SerializedName("user_name") val userName: String,
    @SerializedName("user_email") val userEmail: String,
    @SerializedName("user_foto") val userPhoto: String,
    @SerializedName("user_asal_sekolah") val userSchool: String,
    val kelas: String = "",  // Contoh nilai default untuk kelas
    @SerializedName("date_create") val dateCreated: String,
    @SerializedName("user_gender") val userGender: String,
    @SerializedName("user_status") val userStatus: String
)


data class RegistrationData(
    @SerializedName("nama_lengkap") val namaLengkap: String,
    @SerializedName("email") val email: String,
    @SerializedName("nama_sekolah") val namaSekolah: String,
    @SerializedName("kelas") val kelas: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("foto") val foto: String
)

