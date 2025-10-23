package com.example.dotillos

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.dotillos.core.SupabaseClientManager
import com.example.dotillos.ui.screen.auth.LoginScreen
import com.example.dotillos.ui.screen.auth.RegisterScreen
import com.example.dotillos.ui.screen.patient.MyAppointmentScreen
import com.example.dotillos.ui.screen.common.NotificationScreen
import com.example.dotillos.ui.screen.common.ProfileScreen
import io.github.jan.supabase.auth.auth


@Composable
fun AppNavigation() {
    val session = remember { SupabaseClientManager.supabaseClient.auth.currentSessionOrNull() }
    var isUserLoggedIn by rememberSaveable { mutableStateOf(session != null) }

    var userRole by rememberSaveable { mutableStateOf("Patient") }

    if (!isUserLoggedIn) {
        var currentAuthDestination by rememberSaveable { mutableStateOf(AuthDestinations.LOGIN) }

        Scaffold { innerPadding ->
            when (currentAuthDestination) {
                AuthDestinations.LOGIN -> LoginScreen(
                    modifier = Modifier.padding(innerPadding),
                    onNavigateToRegister = { currentAuthDestination = AuthDestinations.REGISTER }
                )
                AuthDestinations.REGISTER -> RegisterScreen(
                    modifier = Modifier.padding(innerPadding),
                    onNavigateToLogin = { currentAuthDestination = AuthDestinations.LOGIN }
                )
            }
        }
    } else {
        when (userRole) {

            "Patient" -> {
                var patientDestination by rememberSaveable { mutableStateOf(PatientDestinations.MyAppointment) }
                NavigationSuiteScaffold(
                    navigationSuiteItems = {
                        PatientDestinations.entries.forEach { destination ->
                            item(
                                icon = { Icon(destination.icon, contentDescription = destination.label) },
                                selected = destination == patientDestination,
                                onClick = { patientDestination = destination }
                            )
                        }
                    },
                    content = {
                        Scaffold { innerPadding ->
                            when (patientDestination) {
                                PatientDestinations.MyAppointment -> MyAppointmentScreen(Modifier.padding(innerPadding))
                                PatientDestinations.FAVORITES -> FavoritesScreen(Modifier.padding(innerPadding))
                                PatientDestinations.NOTIFICATIONS -> NotificationScreen(Modifier.padding(innerPadding))
                                PatientDestinations.PROFILE -> ProfileScreen(Modifier.padding(innerPadding), onLogout = { isUserLoggedIn = false })
                            }
                        }
                    }
                )
            }

//            "Admin" -> {
//                var adminDestination by rememberSaveable { mutableStateOf(AdminDestinations.DASHBOARD) }
//                NavigationSuiteScaffold(
//                    navigationSuiteItems = {
//                        AdminDestinations.entries.forEach { destination ->
//                            item(
//                                icon = { Icon(destination.icon, contentDescription = destination.label) },
//                                label = { Text(destination.label) },
//                                selected = destination == adminDestination,
//                                onClick = { adminDestination = destination }
//                            )
//                        }
//                    }
//                ) {
//                    Scaffold { innerPadding ->
//                        when (adminDestination) {
//                            AdminDestinations.DASHBOARD -> AdminDashboardScreen(Modifier.padding(innerPadding))
//                            AdminDestinations.USERS -> AdminUsersScreen(Modifier.padding(innerPadding))
//                            AdminDestinations.SETTINGS -> AdminSettingsScreen(Modifier.padding(innerPadding))
//                            AdminDestinations.REPORTS -> AdminReportsScreen(Modifier.padding(innerPadding))
//                            AdminDestinations.LOGS -> AdminLogsScreen(Modifier.padding(innerPadding))
//                        }
//                    }
//                }
//            }
//
//            "Dentist" -> {
//                var dentistDestination by rememberSaveable { mutableStateOf(DentistDestinations.SCHEDULE) }
//                NavigationSuiteScaffold(
//                    navigationSuiteItems = {
//                        DentistDestinations.entries.forEach { destination ->
//                            item(
//                                icon = { Icon(destination.icon, contentDescription = destination.label) },
//                                label = { Text(destination.label) },
//                                selected = destination == dentistDestination,
//                                onClick = { dentistDestination = destination }
//                            )
//                        }
//                    }
//                ) {
//                    Scaffold { innerPadding ->
//                        when (dentistDestination) {
//                            DentistDestinations.SCHEDULE -> DentistScheduleScreen(Modifier.padding(innerPadding))
//                            DentistDestinations.PATIENTS -> DentistPatientsScreen(Modifier.padding(innerPadding))
//                            DentistDestinations.PROFILE -> DentistProfileScreen(Modifier.padding(innerPadding))
//                        }
//                    }
//                }
//            }
//
//            "FrontDesk" -> {
//                var frontDeskDestination by rememberSaveable { mutableStateOf(FrontDeskDestinations.CHECKIN) }
//                NavigationSuiteScaffold(
//                    navigationSuiteItems = {
//                        FrontDeskDestinations.entries.forEach { destination ->
//                            item(
//                                icon = { Icon(destination.icon, contentDescription = destination.label) },
//                                label = { Text(destination.label) },
//                                selected = destination == frontDeskDestination,
//                                onClick = { frontDeskDestination = destination }
//                            )
//                        }
//                    }
//                ) {
//                    Scaffold { innerPadding ->
//                        when (frontDeskDestination) {
//                            FrontDeskDestinations.CHECKIN -> FrontDeskCheckInScreen(Modifier.padding(innerPadding))
//                            FrontDeskDestinations.APPOINTMENTS -> FrontDeskAppointmentsScreen(Modifier.padding(innerPadding))
//                            FrontDeskDestinations.PROFILE -> FrontDeskProfileScreen(Modifier.padding(innerPadding))
//                        }
//                    }
//                }
//            }
        }
    }
}

enum class AuthDestinations(val label: String) {
    LOGIN("Login"),
    REGISTER("Register")
}

enum class PatientDestinations(val label: String, val icon: ImageVector) {
    MyAppointment("MyAppointment", Icons.Default.Home),
    FAVORITES("Favorites", Icons.Default.Favorite),
    NOTIFICATIONS("Notifications", Icons.Default.Notifications),
    PROFILE("Profile", Icons.Default.AccountBox)
}


//
//enum class AdminDestinations(val label: String, val icon: ImageVector) {
//    DASHBOARD("Dashboard", Icons.Default.Home),
//    USERS("Users", Icons.Default.AccountBox),
//    SETTINGS("Settings", Icons.Default.Favorite),
//    REPORTS("Reports", Icons.Default.Home),
//    LOGS("Logs", Icons.Default.Favorite)
//}
//
//enum class DentistDestinations(val label: String, val icon: ImageVector) {
//    SCHEDULE("Schedule", Icons.Default.Home),
//    PATIENTS("Patients", Icons.Default.AccountBox),
//    PROFILE("Profile", Icons.Default.Favorite)
//}
//
//enum class FrontDeskDestinations(val label: String, val icon: ImageVector) {
//    CHECKIN("Check-in", Icons.Default.Home),
//    APPOINTMENTS("Appointments", Icons.Default.Favorite),
//    PROFILE("Profile", Icons.Default.AccountBox)
//}
//





@Composable
fun FavoritesScreen(modifier: Modifier = Modifier) {
    Text("Your favorites here", modifier = modifier)
}