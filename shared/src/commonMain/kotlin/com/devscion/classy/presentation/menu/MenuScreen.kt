package com.devscion.classy.presentation.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devscion.classy.getPlatform
import com.devscion.classy.presentation.menu.components.MenuItem
import com.devscion.classy.ui.AppColors
import com.devscion.classy.ui.LARGE_SPACING
import com.devscion.classy.ui.STANDARD_SPACING
import com.devscion.classy.utils.Horizontal
import com.devscion.classy.utils.Screen
import com.devscion.classy.utils.Vertical
import com.devscion.typistcmp.Typist
import com.devscion.typistcmp.TypistSpeed
import org.jetbrains.compose.resources.painterResource
import visualvoyage.shared.generated.resources.Res
import visualvoyage.shared.generated.resources.app_logo
import visualvoyage.shared.generated.resources.image_annotation
import visualvoyage.shared.generated.resources.txt2img

@Composable
fun MenuScreen(onScreenChanged: (Screen) -> Unit) {

//    val navigator = LocalNavigator.current

    Column(
        Modifier
            .background(AppColors.background)
            .padding(
                horizontal = 20.dp,
                vertical = 50.dp
            )
    ) {

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
                .padding(20.dp)
        ) {
            Image(
                painterResource(Res.drawable.app_logo), "",
                modifier = Modifier.size(50.dp),
            )
            LARGE_SPACING.Horizontal()

            Typist(
                "Visual Voyage",
                typistSpeed = TypistSpeed.FAST,
                isInfiniteCursor = false,
                textStyle = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.textColorWhite
                )
            )
        }
        Column(
            Modifier
                .padding(10.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            MenuItem(
                title = "Text To Image",
                icon = Res.drawable.txt2img
            ) {
                onScreenChanged(Screen.Txt2Img)
            }
            if (getPlatform() != 3) {
                STANDARD_SPACING.Vertical()
                MenuItem(
                    title = "Image Captioning",
                    icon = Res.drawable.image_annotation
                ) {
                    onScreenChanged(Screen.ImageCaptioning)
                }
            }
        }


    }
}

