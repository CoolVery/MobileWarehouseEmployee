package com.example.warehouseemployee.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class ColorPalette(
    val background: Color,
    val background_second: Color,
    val background_for_light_mode: Color,
    val background_important_element: Color,
    val text_color_important_element: Color,
    val background_second_element: Color,
    val text_color_second_element: Color,
    val background_message: Color,
    val text_color_message: Color,
    val text_color_in_cell: Color
    )

val darkTheme = ColorPalette (
    background = Color(0xFF424141),
    background_second = Color(0xFF686666),
    background_for_light_mode = Color(0xFF686666),
    background_important_element = Color(0xFFFBF4F4),
    text_color_important_element = Color(0xFF000000),
    background_second_element = Color(0xFF424141),
    text_color_second_element = Color(0xFFFFFFFF),
    background_message = Color(0xFFD9D9D9),
    text_color_message = Color(0xFF000000),
    text_color_in_cell = Color(0xFFFFFFFF)
)

val lightTheme = ColorPalette (
    background = Color(0xFFDBDBDB),
    background_second = Color(0xFFF5F5F5),
    background_for_light_mode = Color(0xFF9B9999),
    background_important_element = Color(0xFF006BA7),
    text_color_important_element = Color(0xFFFFFFFF),
    background_second_element = Color(0xFF9B9999),
    text_color_second_element = Color(0xFFFFFFFF),
    background_message = Color(0xFFDBDBDB),
    text_color_message = Color(0xFF000000),
    text_color_in_cell = Color(0xFF686666)

)

val LocalColor = staticCompositionLocalOf { darkTheme }