package com.example.dotillos.ui.screen.auth

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.credentials.*
import androidx.compose.ui.platform.LocalContext
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.exceptions.GetCredentialException
import com.example.dotillos.R
import com.example.dotillos.core.AuthRepository
import com.example.dotillos.core.SupabaseClientManager
import com.example.dotillos.ui.theme.AccentGray
import com.example.dotillos.ui.theme.BackgroundWhite
import com.example.dotillos.ui.theme.PrimaryBlue
import com.example.dotillos.ui.theme.SecondaryGreen
import com.google.android.libraries.identity.googleid.*
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.IDToken
import io.github.jan.supabase.exceptions.RestException
import java.security.MessageDigest
import java.util.UUID


@Composable
fun LoginScreen(modifier: Modifier = Modifier, onNavigateToRegister: () -> Unit, loggedIn: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Surface(modifier
        .fillMaxSize()
        .background(BackgroundWhite)) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Dotillos Dental Clinic",
                color = PrimaryBlue,
                fontSize = 28.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = PrimaryBlue,
                    unfocusedIndicatorColor = AccentGray,
                    focusedLabelColor = PrimaryBlue,
                    unfocusedLabelColor = AccentGray,
                    cursorColor = PrimaryBlue
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = PrimaryBlue,
                    unfocusedIndicatorColor = AccentGray,
                    focusedLabelColor = PrimaryBlue,
                    unfocusedLabelColor = AccentGray,
                    cursorColor = PrimaryBlue
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    scope.launch {
                        isLoading = true
                        val result = AuthRepository.login(email, password)
                        isLoading = false

                        result.onSuccess {
                            Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show()
                            loggedIn()
                        }.onFailure { throwable ->
                            val message = when {
                                throwable.message?.contains("email_not_confirmed") == true ->
                                    "Your email is not verified. Please check your inbox"
                                throwable.message?.contains("invalid_credentials") == true ->
                                    "Incorrect email or password"
                                else -> "Login failed: ${throwable.message}"
                            }
                            errorMessage = message
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Text(if (isLoading) "Logging in..." else "Login", color = BackgroundWhite, fontSize = 16.sp)
            }

            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(errorMessage ?: "", color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(12.dp))

            GoogleSignInButton(loggedIn)

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onNavigateToRegister ) {
                Text(
                    "Don't have an account? Register",
                    color = SecondaryGreen,
                    fontSize = 14.sp
                )
            }
        }
    }
}



@Composable
fun GoogleSignInButton(loggedIn: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val onClick: () -> Unit = {
        val credentialManager = CredentialManager.create(context)

        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        val hashedNonce = digest.fold("") { str, it -> str + "%02x".format(it) }

        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(context.getString(R.string.googleClientID))
            .setNonce(hashedNonce)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        coroutineScope.launch {
            try {
                val result = credentialManager.getCredential(context, request)
                val credential = result.credential
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val googleIdToken = googleIdTokenCredential.idToken


                SupabaseClientManager.supabaseClient.auth.signInWith(IDToken) {
                    idToken = googleIdToken
                    provider = Google
                    nonce = rawNonce
                }

                Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                loggedIn()

            } catch (e: GetCredentialException) {
                Toast.makeText(context, "No credentials found: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: GoogleIdTokenParsingException) {
                Toast.makeText(context, "Invalid Google token: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: RestException) {
                Toast.makeText(context, "Supabase error: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, "Unexpected error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = PrimaryBlue,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(width = 1.dp, color = AccentGray)
    ) {
        Text("Sign in with Google", fontSize = 14.sp)
    }
}
