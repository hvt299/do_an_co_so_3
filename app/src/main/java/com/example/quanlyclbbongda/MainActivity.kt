package com.example.quanlyclbbongda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.quanlyclbbongda.contact.AboutUsScreen
import com.example.quanlyclbbongda.contact.ContactInfoScreen
import com.example.quanlyclbbongda.home.HomeScreen
import com.example.quanlyclbbongda.login_signup.LoginScreen
import com.example.quanlyclbbongda.login_signup.SignUpScreen
import com.example.quanlyclbbongda.schedule.ScheduleScreen
import com.example.quanlyclbbongda.schedule.UpdateScheduleScreen
import com.example.quanlyclbbongda.starter.CreateYourTeamScreen
import com.example.quanlyclbbongda.team.GoalChartScreen
import com.example.quanlyclbbongda.team.ResultMatchChartScreen
import com.example.quanlyclbbongda.team.UpdateMemberScreen
import com.example.quanlyclbbongda.team.TeamInfoScreen
import com.example.quanlyclbbongda.team.TeamManagementScreen
import com.example.quanlyclbbongda.ui.theme.QuanLyCLBBongDaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainApp()
        }
    }
}

@Composable
fun MainApp() {
    var navController = rememberNavController()
    QuanLyCLBBongDaTheme {
        NavHost(navController = navController, startDestination = "login") {
            composable("login") {
                LoginScreen(
                    openSignUpScreen = {
                        navController.navigate("signup")
                    },
                    openCreateYourTeamScreen = { email ->
                        navController.navigate("createyourteam/$email")
                    },
                    openHomeScreen = { userEmail, teamID ->
                        navController.navigate("home/$userEmail/$teamID")
                    })
            }

            composable("signup") {
                SignUpScreen(
                    backLoginScreen = {
                        navController.popBackStack()
                    })
            }

            composable("createyourteam/{email}",
                arguments = listOf(
                    navArgument(name = "email") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                val userEmail = backStackEntry.arguments?.getString("email")
                requireNotNull(userEmail)
                CreateYourTeamScreen(
                    userEmail = userEmail,
                    openHomeScreen = { userEmail, teamID ->
                        navController.navigate("home/$userEmail/$teamID")
                    },
                    backLoginScreen = {
                        navController.navigate("login")
                    })
            }

            composable("home/{userEmail}/{teamID}",
                arguments = listOf(
                    navArgument(name = "userEmail") {
                        type = NavType.StringType
                    },
                    navArgument(name = "teamID") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val userEmail = backStackEntry.arguments?.getString("userEmail")
                val teamID = backStackEntry.arguments?.getInt("teamID")
                requireNotNull(userEmail)
                requireNotNull(teamID)
                HomeScreen(
                    userEmail = userEmail,
                    teamID = teamID,
                    Logout = {
                        navController.popBackStack("login", inclusive = false, saveState = true)
                    },
                    openScheduleScreen = { userEmail, teamID ->
                        navController.navigate("schedule/$userEmail/$teamID")
                    },
                    openContactInfo = { userEmail, teamID ->
                        navController.navigate("contactinfo/$userEmail/$teamID")
                    },
                    openTeamInfoScreen = { userEmail, teamID ->
                        navController.navigate("teaminfo/$userEmail/$teamID")
                    },
                    openAboutUsScreen = {
                        navController.navigate("aboutus")
                    },
                    openTeamManagermentScreen = { teamID ->
                        navController.navigate("teammanagement/$teamID")
                    }
                )
            }

            composable("schedule/{userEmail}/{teamID}",
                arguments = listOf(
                    navArgument(name = "userEmail") {
                        type = NavType.StringType
                    },
                    navArgument(name = "teamID") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val userEmail = backStackEntry.arguments?.getString("userEmail")
                val teamID = backStackEntry.arguments?.getInt("teamID")
                requireNotNull(userEmail)
                requireNotNull(teamID)
                ScheduleScreen(
                    userEmail = userEmail,
                    teamID = teamID,
                    Logout = {
                        navController.popBackStack("login", inclusive = false, saveState = true)
                    },
                    backHomeScreen = {
                        navController.popBackStack(
                            "home/{userEmail}/{teamID}",
                            inclusive = false,
                            saveState = true
                        )
                    },
                    openContactInfoScreen = { userEmail, teamID ->
                        navController.navigate("contactinfo/$userEmail/$teamID")
                    },
                    openTeamInfoScreen = { userEmail, teamID ->
                        navController.navigate("teaminfo/$userEmail/$teamID")
                    },
                    openAboutUsScreen = {
                        navController.navigate("aboutus")
                    },
                    openUpdateScheduleScreen = { teamID, scheduleID ->
                        navController.navigate("updateschedule/$teamID/$scheduleID")
                    }
                )
            }

            composable("contactinfo/{userEmail}/{teamID}",
                arguments = listOf(
                    navArgument(name = "userEmail") {
                        type = NavType.StringType
                    },
                    navArgument(name = "teamID") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val userEmail = backStackEntry.arguments?.getString("userEmail")
                val teamID = backStackEntry.arguments?.getInt("teamID")
                requireNotNull(userEmail)
                requireNotNull(teamID)
                ContactInfoScreen(
                    userEmail = userEmail,
                    teamID = teamID,
                    Logout = {
                        navController.popBackStack("login", inclusive = false, saveState = true)
                    },
                    backHomeScreen = {
                        navController.popBackStack(
                            "home/{userEmail}/{teamID}",
                            inclusive = false,
                            saveState = true
                        )
                    },
                    openScheduleScreen = { userEmail, teamID ->
                        navController.navigate("schedule/$userEmail/$teamID")
                    },
                    openTeamInfo = { userEmail, teamID ->
                        navController.navigate("teaminfo/$userEmail/$teamID")
                    },
                    openAboutUsScreen = {
                        navController.navigate("aboutus")
                    }
                )
            }

            composable("teaminfo/{userEmail}/{teamID}",
                arguments = listOf(
                    navArgument(name = "userEmail") {
                        type = NavType.StringType
                    },
                    navArgument(name = "teamID") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val userEmail = backStackEntry.arguments?.getString("userEmail")
                val teamID = backStackEntry.arguments?.getInt("teamID")
                requireNotNull(userEmail)
                requireNotNull(teamID)
                TeamInfoScreen(
                    userEmail = userEmail,
                    teamID = teamID,
                    Logout = {
                        navController.popBackStack("login", inclusive = false, saveState = true)
                    },
                    backHomeScreen = {
                        navController.popBackStack(
                            "home/{userEmail}/{teamID}",
                            inclusive = false,
                            saveState = true
                        )
                    },
                    openContactInfo = { userEmail, teamID ->
                        navController.navigate("contactinfo/$userEmail/$teamID")
                    },
                    openScheduleScreen = { userEmail, teamID ->
                        navController.navigate("schedule/$userEmail/$teamID")
                    },
                    openAboutUsScreen = {
                        navController.navigate("aboutus")
                    },
                )
            }

            composable("aboutus") {
                AboutUsScreen(
                    backHomeScreen = {
                        navController.popBackStack(
                            "home/{userEmail}/{teamID}",
                            inclusive = false,
                            saveState = true
                        )
                    }
                )
            }

            composable("teammanagement/{teamID}",
                arguments = listOf(
                    navArgument(name = "teamID") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val teamID = backStackEntry.arguments?.getInt("teamID")
                requireNotNull(teamID)
                TeamManagementScreen(
                    teamID = teamID,
                    backHomeScreen = {
                        navController.popBackStack(
                            "home/{userEmail}/{teamID}",
                            inclusive = false,
                            saveState = true
                        )
                    },
                    openUpdateMemberScreen = { teamID, memberID ->
                        navController.navigate("updatemember/$teamID/$memberID")
                    },
                    openResultMatchChartScreen = {
                        navController.navigate("resultmatchchart/$teamID")
                    }
                )
            }

            composable("updatemember/{teamID}/{memberID}",
                arguments = listOf(
                    navArgument(name = "teamID") {
                        type = NavType.IntType
                    },
                    navArgument(name = "memberID") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val teamID = backStackEntry.arguments?.getInt("teamID")
                val memberID = backStackEntry.arguments?.getInt("memberID")
                requireNotNull(teamID)
                requireNotNull(memberID)
                UpdateMemberScreen(
                    teamID = teamID,
                    memberID = memberID,
                    backTeamManagementScreen = {
                        navController.popBackStack(
                            "teammanagement/{teamID}",
                            inclusive = false,
                            saveState = true
                        )
                    }
                )
            }

            composable("updateschedule/{teamID}/{scheduleID}",
                arguments = listOf(
                    navArgument(name = "teamID") {
                        type = NavType.IntType
                    },
                    navArgument(name = "scheduleID") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val teamID = backStackEntry.arguments?.getInt("teamID")
                val scheduleID = backStackEntry.arguments?.getInt("scheduleID")
                requireNotNull(teamID)
                requireNotNull(scheduleID)
                UpdateScheduleScreen(
                    teamID = teamID,
                    scheduleID = scheduleID,
                    backScheduleScreen = {
                        navController.popBackStack(
                            "schedule/{userEmail}/{teamID}",
                            inclusive = false,
                            saveState = true
                        )
                    }
                )
            }

            composable("resultmatchchart/{teamID}",
                arguments = listOf(
                    navArgument(name = "teamID") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val teamID = backStackEntry.arguments?.getInt("teamID")
                requireNotNull(teamID)
                ResultMatchChartScreen(
                    teamID = teamID,
                    backTeamManagementScreen = {
                        navController.popBackStack(
                            "teammanagement/{teamID}",
                            inclusive = false,
                            saveState = true
                        )
                    },
                    openGoalChartScreen = {
                        navController.navigate("goalchart/$teamID")
                    }
                )
            }

            composable("goalchart/{teamID}",
                arguments = listOf(
                    navArgument(name = "teamID") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val teamID = backStackEntry.arguments?.getInt("teamID")
                requireNotNull(teamID)
                GoalChartScreen(
                    teamID = teamID,
                    backTeamManagementScreen = {
                        navController.popBackStack(
                            "teammanagement/{teamID}",
                            inclusive = false,
                            saveState = true
                        )
                    },
                    backResultMatchChartScreen = {
                        navController.popBackStack(
                            "resultmatchchart/{teamID}",
                            inclusive = false,
                            saveState = true
                        )
                    }
                )
            }
        }
    }
}