package com.example.dotillos.core

import com.example.dotillos.models.RegisterResult
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.user.UserSession
import kotlinx.serialization.json.buildJsonObject
import org.slf4j.MDC.put
import kotlin.time.ExperimentalTime


object AuthRepository {
    private val client = SupabaseClientManager.supabaseClient

    suspend fun login(email: String, password: String): Result<UserSession> {
        try {
            client.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            val session = client.auth.currentSessionOrNull()
                ?: return Result.failure(Exception("No active session"))
            return Result.success(session)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    @OptIn(ExperimentalTime::class)
    suspend fun register(email: String, password: String, name: String? = null): RegisterResult {
        val signUpResponse = client.auth.signUpWith(Email) {
            this.email = email
            this.password = password
            data = buildJsonObject {
                put("name", name)
                put("role", "Patient")
            }
        }

        if (signUpResponse != null && signUpResponse.emailConfirmedAt == null) {
            return RegisterResult(success = true, requiresVerification = true)
        }

        return RegisterResult(success = true)

    }


    suspend fun logout() {
        client.auth.signOut()
    }
}


