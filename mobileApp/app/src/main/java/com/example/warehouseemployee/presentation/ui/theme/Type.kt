package com.example.warehouseemployee.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.warehouseemployee.R

data class Typography(
    val primaryTitle: TextStyle,
    val secondText: TextStyle,
    val smallText: TextStyle,
    val textField: TextStyle
)


val typography = Typography(
    //Текст в кнопках и важных заголовках
    primaryTitle = TextStyle(
        fontFamily = FontFamily(Font(R.font.magnet_trial_bold)),
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    //Текст в остальных элементах
    secondText = TextStyle(
        fontFamily = FontFamily(Font(R.font.seravek_bold)),
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    //Текст в сообщениях и малых деталях
    smallText = TextStyle(
        fontFamily = FontFamily(Font(R.font.seravek)),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    textField = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_bold)),
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

val LocalTypography = staticCompositionLocalOf { typography }
