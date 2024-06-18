package com.devscion.classy.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devscion.classy.ui.AppColors
import com.devscion.classy.utils.Screen
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import visualvoyage.shared.generated.resources.Res
import visualvoyage.shared.generated.resources.app_logo

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SplashScreen(onScreenChanged : (Screen)->Unit) {
    LaunchedEffect(Unit) {
        delay(1000)
        onScreenChanged(Screen.Menu)
    }
    Box(
        Modifier.fillMaxSize()
            .background(AppColors.background),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painterResource(Res.drawable.app_logo),
            contentDescription = "App Logo",
            modifier = Modifier.size(200.dp)
        )
    }
}