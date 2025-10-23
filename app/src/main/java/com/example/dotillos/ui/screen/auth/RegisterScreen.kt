package com.example.dotillos.ui.screen.auth

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
fun RegisterScreen(modifier: Modifier = Modifier, onNavigateToLogin: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val passwordPattern = Regex("^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,}$")
    val emailPattern = Regex("^[A-Za-z].*@.+\\..+$")
    var registrationComplete by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    Surface(
        modifier
        .fillMaxSize()
        .background(BackgroundWhite)
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!registrationComplete) {
                Text(
                    "Create an Account",
                    color = PrimaryBlue,
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full Name") },
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

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password") },
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
                            errorMessage = null

                            if (!emailPattern.matches(email)) {
                                errorMessage = "Invalid email format"
                                return@launch
                            }

                            if (!passwordPattern.matches(password)) {
                                errorMessage = "Password must be 8+ chars, include uppercase, number, and special char"
                                return@launch
                            }

                            if (password != confirmPassword) {
                                errorMessage = "Passwords do not match"
                                return@launch
                            }

                            isLoading = true

                            val result = AuthRepository.register(email, password, name)

                            isLoading = true

                            if (result.success) {
                                if (result.requiresVerification) {
                                    registrationComplete = true
                                }
                            } else {
                                errorMessage = result.errorMessage
                            }

                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = !isLoading,
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(if (isLoading) "Registering..." else "Register", color = BackgroundWhite, fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = onNavigateToLogin ) {
                    Text(
                        "Already have an account? Login",
                        color = SecondaryGreen,
                        fontSize = 14.sp
                    )
                }

                if (errorMessage != null) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(errorMessage ?: "", color = MaterialTheme.colorScheme.error)
                }


            } else {
                Text(
                    "Registration successful! Please check your email to verify your account before logging in.",
                    textAlign = TextAlign.Center,
                    color = PrimaryBlue,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = onNavigateToLogin,
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text("Back to Login", color = BackgroundWhite)
                }
            }
        }
    }
}

