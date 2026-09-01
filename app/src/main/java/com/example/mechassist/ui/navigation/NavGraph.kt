package com.example.mechassist.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mechassist.data.repository.ProfileRepository
import com.example.mechassist.ui.screens.BookingFormScreen
import com.example.mechassist.ui.screens.DetailScreen
import com.example.mechassist.ui.screens.MainScreen
import com.example.mechassist.ui.screens.ProfileSetupScreen
import com.example.mechassist.ui.screens.SplashScreen
import com.example.mechassist.viewmodel.MechanicViewModel
import kotlinx.coroutines.launch
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object NavRoutes {
    const val SPLASH = "splash"
    const val PROFILE_SETUP = "profile_setup"
    const val MAIN = "main"
    const val DETAIL = "detail/{mechanicName}"
    const val BOOKING = "booking/{mechanicName}"

    fun detailRoute(mechanicName: String): String {
        val encoded = URLEncoder.encode(mechanicName, StandardCharsets.UTF_8.toString())
        return "detail/$encoded"
    }

    fun bookingRoute(mechanicName: String): String {
        val encoded = URLEncoder.encode(mechanicName, StandardCharsets.UTF_8.toString())
        return "booking/$encoded"
    }
}

@Composable
fun AppNavGraph(
    navController: NavHostController,
    profileRepository: ProfileRepository,
    mechanicViewModel: MechanicViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.SPLASH
    ) {
        composable(NavRoutes.SPLASH) {
            SplashScreen(
                onNavigate = {
                    coroutineScope.launch {
                        val profile = profileRepository.getProfileOnce()
                        if (profile != null) {
                            navController.navigate(NavRoutes.MAIN) {
                                popUpTo(NavRoutes.SPLASH) { inclusive = true }
                            }
                        } else {
                            navController.navigate(NavRoutes.PROFILE_SETUP) {
                                popUpTo(NavRoutes.SPLASH) { inclusive = true }
                            }
                        }
                    }
                }
            )
        }

        composable(NavRoutes.PROFILE_SETUP) {
            ProfileSetupScreen(
                onNavigateToMain = {
                    navController.navigate(NavRoutes.MAIN) {
                        popUpTo(NavRoutes.PROFILE_SETUP) { inclusive = true }
                    }
                }
            )
        }

        composable(NavRoutes.MAIN) {
            MainScreen(
                mechanicViewModel = mechanicViewModel,
                onMechanicClick = { mechanicName ->
                    navController.navigate(NavRoutes.detailRoute(mechanicName))
                }
            )
        }

        composable(
            route = NavRoutes.DETAIL,
            arguments = listOf(navArgument("mechanicName") { type = NavType.StringType })
        ) { backStackEntry ->
            val encodedName = backStackEntry.arguments?.getString("mechanicName") ?: ""
            val mechanicName = URLDecoder.decode(encodedName, StandardCharsets.UTF_8.toString())
            val mechanic = mechanicViewModel.getMechanicByName(mechanicName)

            DetailScreen(
                mechanic = mechanic,
                onBackClick = { navController.popBackStack() },
                onBookClick = {
                    navController.navigate(NavRoutes.bookingRoute(mechanicName))
                }
            )
        }

        composable(
            route = NavRoutes.BOOKING,
            arguments = listOf(navArgument("mechanicName") { type = NavType.StringType })
        ) { backStackEntry ->
            val encodedName = backStackEntry.arguments?.getString("mechanicName") ?: ""
            val mechanicName = URLDecoder.decode(encodedName, StandardCharsets.UTF_8.toString())
            val mechanic = mechanicViewModel.getMechanicByName(mechanicName)

            BookingFormScreen(
                mechanicName = mechanicName,
                mechanicLocation = mechanic?.location ?: "",
                mechanicServices = mechanic?.services ?: "",
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
