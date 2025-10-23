package com.example.dotillos.ui.screen.auth

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dotillos.core.AuthRepository
import com.example.dotillos.ui.theme.AccentGray
import com.example.dotillos.ui.theme.BackgroundWhite
import com.example.dotillos.ui.theme.PrimaryBlue
import com.example.dotillos.ui.theme.SecondaryGreen
import kotlinx.coroutines.launch

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

            OutlinedButton(
                onClick = {
//                    scope.launch {
//                        try {
//                            client.auth.signInWith(
//                                Google,
//                                redirectUrl = "smartdental://auth/callback"
//                            )
//                        } catch (e: Exception) {
//                            e.printStackTrace()
//                        }
//                        isLoading = true
//                        try {
//                            AuthRepository.loginWithGoogle()
//                        } catch (e: Exception) {
//                            errorMessage = "Google login failed: ${e.message}"
//                        } finally {
//                            isLoading = false
//                        }
//                    }
                },
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