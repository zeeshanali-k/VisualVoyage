package com.devscion.classy.presentation.image_captioning.image_annotation_results

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devscion.classy.ui.AppColors
import com.devscion.classy.ui.LARGE_SPACING
import com.devscion.classy.ui.STANDARD_ICON_SIZE
import com.devscion.classy.ui.STANDARD_SPACING
import com.devscion.classy.utils.Vertical
import com.devscion.typistcmp.Typist
import com.devscion.typistcmp.TypistSpeed


@Composable
fun ImageCaptioningResultsScreen(
    result: String,
    onPopped: () -> Unit
) {

//    val navigator = LocalNavigator.current
    Column(
        Modifier.fillMaxSize()
            .background(AppColors.background)
            .padding(20.dp)
    ) {
        Box(
            Modifier.fillMaxWidth(),
        ) {

            Icon(
                Icons.AutoMirrored.Rounded.ArrowBack,
                "",
                tint = Color.White,
                modifier = Modifier.clickable {
                    onPopped()
                }.size(STANDARD_ICON_SIZE)
                    .align(Alignment.TopStart)
            )

            Typist(
                "Annotation Results",
                typistSpeed = TypistSpeed.EXTRA_FAST,
                isInfiniteCursor = false,
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }

        LARGE_SPACING.Vertical()

        Card(
            Modifier.fillMaxWidth()
                .weight(1f),
            colors = CardDefaults.cardColors()
                .copy(
                    containerColor = AppColors.background.copy(alpha = 0.75f)
                )
        ) {

            Column(
                Modifier.fillMaxSize()
                    .padding(10.dp)
            ) {

                Text(
                    "Results", style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                STANDARD_SPACING.Vertical()

                Typist(
                    result,
                    typistSpeed = TypistSpeed.EXTRA_FAST,
                    isInfiniteCursor = false,
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        color = Color.White,
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }

    }
}