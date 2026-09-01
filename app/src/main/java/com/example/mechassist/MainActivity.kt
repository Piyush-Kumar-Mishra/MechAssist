package com.example.mechassist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.mechassist.data.repository.ProfileRepository
import com.example.mechassist.ui.navigation.AppNavGraph
import com.example.mechassist.ui.theme.MechAssistTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var profileRepository: ProfileRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MechAssistTheme {
                val navController = rememberNavController()
                AppNavGraph(
                    navController = navController,
                    profileRepository = profileRepository
                )
            }
        }
    }
}
