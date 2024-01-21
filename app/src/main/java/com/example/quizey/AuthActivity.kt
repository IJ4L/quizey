package com.example.quizey

import ApiResponse
import RegistrationData
import RetrofitClient
import UserData
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val apiService = RetrofitClient.retrofit.create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        auth = FirebaseAuth.getInstance()

        val googleLoginButton: Button = findViewById(R.id.google_login)

        googleLoginButton.setOnClickListener {
            signInWithGoogle()
        }
    }

    private fun signInWithGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)

        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleGoogleSignInResult(task)
        }
    }

    private fun handleGoogleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)!!

            if (account.email != null) {
                val apiService = RetrofitClient.retrofit.create(ApiService::class.java)
                val call = apiService.getUserData(account.email!!)

                call.enqueue(object : Callback<UserData> {
                    override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                        if (response.isSuccessful) {
                            val userData = response.body()
                            if (userData!!.userId != "0") {
                                val intent = Intent(this@AuthActivity, HomeActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                registerUserAutomatically(account)
                            }
                        } else {
                            Log.e("APICallError", "API call failed with code: ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<UserData>, t: Throwable) {
                        Log.e("APICallError", "Error calling API: ${t.message}", t)
                    }
                })

            } else {
                Log.e("APICallError", "User email is null")
                Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
            }

        } catch (e: ApiException) {
            Log.e("GoogleSignInError", "Status code: ${e.statusCode}, Message: ${e.message}")
            Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun registerUserAutomatically(account: GoogleSignInAccount) {
        val registrationData = RegistrationData(
            namaLengkap = account.displayName ?: "Default Name",
            email = account.email ?: "",
            namaSekolah = "Default School",
            kelas = "Default Class",
            gender = "Default Gender",
            foto = "Default Photo URL"
        )

        performRegistration(registrationData)
    }

    private fun performRegistration(registrationData: RegistrationData) {
        val apiService = RetrofitClient.retrofit.create(ApiService::class.java)

        val call = apiService.registerUser(
            registrationData
        )

        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse?.status == 1) {
                        val intent = Intent(this@AuthActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.e("RegistrationError", "Registration failed with message: ${apiResponse?.message}")
                        Toast.makeText(this@AuthActivity, "Registration Failed", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("RegistrationError", "API call failed with code: ${response.code()}")
                    Toast.makeText(this@AuthActivity, "Registration Failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("RegistrationError", "Error calling registration API: ${t.message}", t)
                Toast.makeText(this@AuthActivity, "Registration Failed", Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        private const val RC_SIGN_IN = 9001
    }
}
