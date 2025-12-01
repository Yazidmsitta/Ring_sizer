package com.ringsize.app.data.repository

import com.ringsize.app.data.remote.ApiService
import com.ringsize.app.data.remote.model.AuthResponse
import com.ringsize.app.data.remote.model.LoginRequest
import com.ringsize.app.data.remote.model.RegisterRequest
import com.ringsize.app.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException

class AuthRepository(private val apiService: ApiService) {
    suspend fun login(email: String, password: String): Result<AuthResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.login(LoginRequest(email, password))
                if (response.isSuccessful && response.body() != null) {
                    Result.Success(response.body()!!)
                } else {
                    val errorBody = try {
                        response.errorBody()?.string() ?: response.message()
                    } catch (e: Exception) {
                        response.message() ?: "Erreur inconnue"
                    }
                    Result.Error(Exception("Erreur serveur: $errorBody (Code: ${response.code()})"))
                }
            } catch (e: java.net.UnknownHostException) {
                Result.Error(Exception("Impossible de se connecter au serveur. Vérifiez que le serveur Laravel est démarré."))
            } catch (e: java.net.ConnectException) {
                Result.Error(Exception("Connexion refusée. Vérifiez que le serveur Laravel écoute sur http://10.0.2.2:8000"))
            } catch (e: com.google.gson.JsonSyntaxException) {
                Result.Error(Exception("Erreur de format de réponse du serveur: ${e.message}"))
            } catch (e: Exception) {
                Result.Error(Exception("Erreur de connexion: ${e.message ?: e.javaClass.simpleName}"))
            }
        }
    }
    
    suspend fun register(name: String, email: String, password: String, passwordConfirmation: String): Result<AuthResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.register(RegisterRequest(name, email, password, passwordConfirmation))
                if (response.isSuccessful && response.body() != null) {
                    Result.Success(response.body()!!)
                } else {
                    val errorBody = try {
                        response.errorBody()?.string() ?: response.message()
                    } catch (e: Exception) {
                        response.message() ?: "Erreur inconnue"
                    }
                    Result.Error(Exception("Erreur serveur: $errorBody (Code: ${response.code()})"))
                }
            } catch (e: java.net.UnknownHostException) {
                Result.Error(Exception("Impossible de se connecter au serveur. Vérifiez que le serveur Laravel est démarré."))
            } catch (e: java.net.ConnectException) {
                Result.Error(Exception("Connexion refusée. Vérifiez que le serveur Laravel écoute sur http://10.0.2.2:8000"))
            } catch (e: com.google.gson.JsonSyntaxException) {
                Result.Error(Exception("Erreur de format de réponse du serveur: ${e.message}"))
            } catch (e: Exception) {
                Result.Error(Exception("Erreur de connexion: ${e.message ?: e.javaClass.simpleName}"))
            }
        }
    }
    
    suspend fun logout(token: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.logout("Bearer $token")
                if (response.isSuccessful) {
                    Result.Success(Unit)
                } else {
                    Result.Error(Exception(response.message()))
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
    
    suspend fun forgotPassword(email: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.forgotPassword(com.ringsize.app.data.remote.model.ForgotPasswordRequest(email))
                if (response.isSuccessful) {
                    Result.Success(Unit)
                } else {
                    Result.Error(Exception(response.message()))
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
}

