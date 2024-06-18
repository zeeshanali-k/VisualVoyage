package com.devscion.classy.ui


import androidx.compose.ui.graphics.Color

object AppColors {
    val ToastBackground = Color(23, 23, 23)
    val background = Color(0xFF241E20)
    val textColorBlack = Color.Black
    val textColorWhite = Color.White
    val iconColor = Color.White
    val onBackground = Color(0xFFD3D3D3)

    val fullScreenImageBackground = Color(0xFF19191C)
    val filterButtonsBackground = fullScreenImageBackground.copy(alpha = 0.7f)
    val uiLightBlack = Color(25, 25, 28).copy(alpha = 0.7f)
    val noteBlockBackground = Color(0xFFF3F3F4)
}

//@Composable
//fun ImageViewerTheme(content: @Composable () -> Unit) {
//    isSystemInDarkTheme() // todo check and change colors
//    MaterialTheme(
//        colorScheme = MaterialTheme.colorScheme.copy(
//            background = ImageviewerColors.background,
//            onBackground = ImageviewerColors.onBackground
//        )
//    ) {
//        ProvideTextStyle(LocalTextStyle.current.copy(letterSpacing = 0.sp)) {
//            content()
//        }
//    }
//}
